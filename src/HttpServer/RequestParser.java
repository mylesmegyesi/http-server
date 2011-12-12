package HttpServer;

import HttpRequestParser.Exceptions.ParseException;
import HttpRequestParser.HttpRequestParser;
import HttpServer.Exceptions.BadRequestException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Author: Myles Megyesi
 */
public class RequestParser {

    HttpRequestParser requestParser;

    public RequestParser() {
        this.requestParser = new HttpRequestParser();
    }

    public RequestParser(HttpRequestParser requestParser) {
        this.requestParser = requestParser;
    }

    public Request parseRequestLineAndHeaders(InputStream inputStream) throws IOException, BadRequestException {
        try {
            return new Request(this.requestParser.parseRequestLineAndHeaders(inputStream));
        } catch (ParseException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public Map<String, Object> parseBody(InputStream inputStream, String contentType, int contentLength) throws IOException, BadRequestException {
        try {
            return this.requestParser.parseBody(inputStream, contentType, contentLength);
        } catch (ParseException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
