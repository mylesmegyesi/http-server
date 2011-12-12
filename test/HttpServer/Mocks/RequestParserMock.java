package HttpServer.Mocks;

import HttpServer.Exceptions.BadRequestException;
import HttpServer.Request;
import HttpServer.RequestParser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Myles Megyesi
 */
public class RequestParserMock extends RequestParser {

    public int parseHeadersCalledCount = 0;
    public int parseBodyCalledCount = 0;
    public boolean throwOnParse = false;
    public String action = "GET";
    public String version = "HTTP/1.1";

    public RequestParserMock(boolean throwOnParse) {
        this.throwOnParse = throwOnParse;
    }

    @Override
    public Request parseRequestLineAndHeaders(InputStream inputStream) throws BadRequestException {
        this.parseHeadersCalledCount++;
        if (this.throwOnParse) {
            throw new BadRequestException("Whoops!");
        }
        Map<String, String> requestHeaders = new HashMap<String, String>();
        requestHeaders.put("Content-Type", "text/html");
        requestHeaders.put("Content-Length", "0");
        return new RequestMock(this.action, this.version, requestHeaders);
    }

    @Override
    public Map<String, Object> parseBody(InputStream inputStream, String contentType, int contentLength) {
        this.parseBodyCalledCount++;
        return null;
    }

}
