package HttpServer.Mocks;

import HttpServer.Exceptions.BadRequestException;
import HttpServer.Request;
import HttpServer.RequestParser;
import HttpServer.Utility.InputStreamReader;

import java.io.InputStream;

/**
 * Author: Myles Megyesi
 */
public class RequestParserMock extends RequestParser {

    public boolean throwOnParse = false;

    public RequestParserMock(boolean throwOnParse) {
        this.throwOnParse = throwOnParse;
    }

    @Override
    public Request parse(InputStream inputStream, InputStreamReader inputStreamReader) throws BadRequestException {
        this.calledCount++;
        if (this.throwOnParse) {
            throw new BadRequestException("Whoops!");
        }
        return new RequestMock();
    }

    public int getCalledCount() {
        return calledCount;
    }

    private int calledCount = 0;

}
