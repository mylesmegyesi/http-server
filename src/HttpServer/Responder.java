package HttpServer;

import HttpServer.Exceptions.ResponseException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public abstract class Responder {

    public Responder(Logger logger) {
        this.logger = logger;
    }

    public abstract boolean canRespond(Request request);

    public abstract Response getResponse(Request request) throws ResponseException;

    public void respond(OutputStream outputStream, Request request) throws ResponseException {
        this.logger.info(String.format("Responding to %s %s", request.getAction(), request.getRequestUri()));
        Response response = this.getResponse(request);
        try {
            response.writeStatusLine(outputStream);
            response.writeHeaders(outputStream);
            response.writeBody(outputStream);
        } catch (IOException e) {
            throw new ResponseException(e.getMessage());
        }
    }

    protected Logger logger;
}
