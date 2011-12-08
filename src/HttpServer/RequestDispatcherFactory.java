package HttpServer;


import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class RequestDispatcherFactory implements SocketServer.RequestDispatcherFactory {

    public RequestDispatcherFactory(RequestParser requestParser, ResponseWriter responseWriter, List<RequestHandler> requestHandlers, Response notFoundResponse, Logger logger) {
        this.requestParser = requestParser;
        this.responseWriter = responseWriter;
        this.requestHandlers = requestHandlers;
        this.notFoundResponse = notFoundResponse;
        this.logger = logger;
    }

    public Runnable create(Socket socket) {
        return new RequestDispatcher(socket, this.requestParser, this.responseWriter, this.requestHandlers, this.notFoundResponse, this.logger);
    }

    private RequestParser requestParser;
    private ResponseWriter responseWriter;
    private List<RequestHandler> requestHandlers;
    private Response notFoundResponse;
    private Logger logger;
}
