package HttpServer;


import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class RequestDispatcherFactory implements SocketServer.RequestDispatcherFactory {

    public RequestDispatcherFactory(RequestParser requestParser, ResponseWriter responseWriter, List<RequestHandler> requestHandlers, Logger logger) {
        this.requestParser = requestParser;
        this.responseWriter = responseWriter;
        this.requestHandlers = requestHandlers;
        this.logger = logger;
    }

    public Runnable create(Socket socket) {
        return new RequestDispatcher(socket, this.requestParser, this.responseWriter, this.requestHandlers, this.logger);
    }

    private RequestParser requestParser;
    private ResponseWriter responseWriter;
    private List<RequestHandler> requestHandlers;
    private Logger logger;
}
