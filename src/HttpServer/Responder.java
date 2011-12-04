package HttpServer;

import SocketServer.Exceptions.ResponseException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Author: Myles Megyesi
 */
public abstract class Responder implements SocketServer.Responder {

    public abstract boolean canHandle(Request request);

    public boolean canHandle(SocketServer.Request request) {
        return this.canHandle((Request) request);
    }

    public abstract Response getResponse(Request request) throws ResponseException;

    public void handle(OutputStream outputStream, SocketServer.Request request) throws ResponseException {
        Response response = this.getResponse((Request) request);
        String respondWith = response.getResponseString();
        try {
            outputStream.write(respondWith.getBytes());
        } catch (IOException e) {
            throw new ResponseException(e.getMessage());
        }
    }
}
