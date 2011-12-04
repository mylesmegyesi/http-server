package HttpServer.Mocks;

import HttpServer.Exceptions.ResponseException;
import HttpServer.Request;
import HttpServer.Responder;
import HttpServer.Response;

import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class ResponderMock extends Responder {

    public ResponderMock(boolean canHandle, Logger logger) {
        super(logger);
        this.canHandle = canHandle;
    }

    @Override
    public boolean canRespond(Request request) {
        return this.canHandle;
    }

    @Override
    public Response getResponse(Request request) throws ResponseException {
        return null;
    }

    @Override
    public void respond(OutputStream outputStream, Request request) {
        this.calledCount++;
    }


    public void setCanHandle(boolean canHandle) {
        this.canHandle = canHandle;
    }

    public int getCalledCount() {
        return calledCount;
    }

    private boolean canHandle;
    private int calledCount = 0;
}
