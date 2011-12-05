package HttpServer.Mocks;

import HttpServer.Request;
import HttpServer.RequestHeader;
import HttpServer.RequestParser;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Author: Myles Megyesi
 */
public class RequestParserMock extends RequestParser{

    @Override
    public Request parse(InputStream inputStream) {
        this.calledCount++;
        return new RequestMock("", "", "", "", new ArrayList<RequestHeader>(), "", "");
    }

    public int getCalledCount() {
        return calledCount;
    }

    private int calledCount = 0;

}
