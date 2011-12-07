package HttpServer.Mocks;

import HttpServer.Exceptions.ResponseException;
import HttpServer.Request;
import HttpServer.RequestHandler;
import HttpServer.Response;

/**
 * Author: Myles Megyesi
 */
public class RequestHandlerMock implements RequestHandler {

    public int getResponseCalledCount = 0;
    public int canHandleCalledCount = 0;

    public RequestHandlerMock(boolean canHandle, boolean throwException) {
        this.canHandle = canHandle;
        this.throwException = throwException;
    }

    public boolean canRespond(Request request) {
        this.canHandleCalledCount++;
        return this.canHandle;
    }

    public Response getResponse(Request request) throws ResponseException {
        this.getResponseCalledCount++;
        if (this.throwException) {
            throw new ResponseException("Whoops!");
        }
        return null;
    }

    private boolean canHandle;
    private boolean throwException;
}
