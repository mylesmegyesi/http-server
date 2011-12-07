package HttpServer;

import HttpServer.Exceptions.BadRequestException;
import HttpServer.Utility.InputStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Myles Megyesi
 */
public class RequestParser {

    public Request parse(InputStream inputStream, InputStreamReader inputStreamReader) throws IOException, BadRequestException {
        return new Request(this.parseRequestLine(inputStream, inputStreamReader), this.parseHeaders(inputStream, inputStreamReader), inputStream);
    }

    public RequestLine parseRequestLine(InputStream inputStream, InputStreamReader inputStreamReader) throws IOException, BadRequestException {
        String requestLine = inputStreamReader.getNextLine(inputStream);
        String[] requestLineItems = this.splitStringOnWhitespace(requestLine);
        if (requestLineItems.length != 3) {
            throw new BadRequestException(String.format("Improperly formatted request line: %s", requestLine));
        }
        return this.parseRequestItems(requestLineItems);
    }

    public String[] splitStringOnWhitespace(String requestLine) {
        return requestLine.trim().split("\\s+");
    }

    public RequestLine parseRequestItems(String[] requestLineItems) throws IOException, BadRequestException {
        String[] requestUriItems = parseRequestUri(requestLineItems[1]);
        return new RequestLine(requestLineItems[0], requestUriItems[0], requestUriItems[1], requestLineItems[2]);
    }

    public String[] parseRequestUri(String requestUri) {
        String[] requestUriItems = new String[]{requestUri, ""};
        int questionMarkIndex = requestUri.indexOf("?");
        if (questionMarkIndex != -1) {
            requestUriItems[0] = requestUri.substring(0, questionMarkIndex);
            requestUriItems[1] = requestUri.substring(questionMarkIndex + 1, requestUri.length());
        }
        return requestUriItems;
    }

    public List<RequestHeader> parseHeaders(InputStream inputStream, InputStreamReader inputStreamReader) throws BadRequestException, IOException {
        List<RequestHeader> requestHeaders = new ArrayList<RequestHeader>();
        String nextLine;
        while (!(nextLine = inputStreamReader.getNextLine(inputStream)).equals("")) { // headers are terminated by an empty line
            requestHeaders.add(this.parseHeader(nextLine));
        }
        return requestHeaders;
    }

    public RequestHeader parseHeader(String headerLine) throws BadRequestException {
        int colonIndex = headerLine.indexOf(':');
        if (colonIndex == -1 || colonIndex == 0 || colonIndex == headerLine.length() - 1) {
            throw new BadRequestException(String.format("Improperly formatted header: %s", headerLine));
        }
        return new RequestHeader(headerLine.substring(0, colonIndex), headerLine.substring(colonIndex + 1, headerLine.length()).trim());
    }
}
