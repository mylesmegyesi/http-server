package HttpServer;

import HttpServer.Exceptions.ResponseException;

/**
 * Author: Myles Megyesi
 */
public interface RequestHandler {

    public boolean canRespond(Request request);

    public Response getResponse(Request request) throws ResponseException;
}
