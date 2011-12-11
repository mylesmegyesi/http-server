package HttpServer.Mocks;

import HttpRequestParser.Exceptions.ParseException;
import HttpRequestParser.HttpRequest;
import HttpRequestParser.HttpRequestParser;

import java.io.InputStream;
import java.util.Map;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestParserMock extends HttpRequestParser {

    public int parseHeadersCalledCount = 0;
    public int parseBodyCalledCount = 0;
    public boolean throwsOnParse = false;

    @Override
    public HttpRequest parseRequestLineAndHeaders(InputStream inputStream) throws ParseException {
        this.parseHeadersCalledCount++;
        if (this.throwsOnParse) {
            throw new ParseException("Whoops");
        }
        return new HttpRequestMock();
    }

    @Override
    public Map<String, Object> parseBody(InputStream inputStream, String contentType) throws ParseException {
        this.parseBodyCalledCount++;
        if (this.throwsOnParse) {
            throw new ParseException("Whoops");
        }
        return null;
    }
}
