package HttpServer;

import HttpServer.Responses.NotFound;
import SocketServer.SocketServer;
import SocketServer.Utility.Logging;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class HttpServer {

    public int port;
    public List<RequestHandler> requestHandlers;
    public SocketServer socketServer;

    public HttpServer(int port) {
        this.port = port;
        this.requestHandlers = new ArrayList<RequestHandler>();
    }

    public HttpServer(int port, List<RequestHandler> requestHandlers) {
        this.port = port;
        this.requestHandlers = requestHandlers;
    }

    public void start() {
        synchronized (startUpShutdownLock) {
            Logger logger = Logging.getLoggerAndSetLevel(this.getClass().getName(), Level.ALL);
            initSocketServer(this.port, new RequestDispatcherFactory(new RequestParser(), new ResponseWriter(), this.requestHandlers, new NotFound(), logger), logger);
        }
        this.socketServer.startListening();
    }

    public void initSocketServer(int port, RequestDispatcherFactory requestDispatcherFactory, Logger logger) {
        if (this.socketServer == null) {
            this.socketServer = new SocketServer(port, requestDispatcherFactory, logger);
        }
    }

    public void stop() {
        synchronized (startUpShutdownLock) {
            if (this.socketServer != null) {
                this.socketServer.stopListening();
                this.socketServer = null;
            }
        }
    }

    private Object startUpShutdownLock = new Object();
}
