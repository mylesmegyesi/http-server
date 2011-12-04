package HttpServer;


import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class RequestHandlerFactory implements SocketServer.RequestHandlerFactory {

    public RequestHandlerFactory(RequestParser requestParser, List<Responder> responders, Response notFoundResponse, Logger logger) {
        this.requestParser = requestParser;
        this.responders = responders;
        this.notFoundResponse = notFoundResponse;
        this.logger = logger;
    }

    public Runnable create(Socket socket) {
        return new RequestHandler(socket, this.requestParser, this.responders, this.notFoundResponse, this.logger);
    }

    private RequestParser requestParser;
    private List<Responder> responders;
    private Response notFoundResponse;
    private Logger logger;
}
