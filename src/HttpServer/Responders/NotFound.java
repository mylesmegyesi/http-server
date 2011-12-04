package HttpServer.Responders;

import HttpServer.Request;
import HttpServer.Responder;
import HttpServer.Response;
import HttpServer.ResponseHeader;
import SocketServer.Exceptions.ResponseException;

import java.util.ArrayList;

/**
 * Author: Myles Megyesi
 */
public class NotFound extends Responder {

    @Override
    public boolean canHandle(Request request) {
        return true;
    }

    @Override
    public Response getResponse(Request request) throws ResponseException {
        return new Response("HTTP/1.1", 404, "Not Found", new ArrayList<ResponseHeader>(), null);
    }
}
