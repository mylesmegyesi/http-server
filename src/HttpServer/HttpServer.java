package HttpServer;

import HttpServer.Utility.Logging;
import SocketServer.SocketServer;

import java.io.IOException;
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

    public void start() throws IOException {
        synchronized (startUpShutdownLock) {
            Logger logger = new Logging().getLoggerAndSetLevel(this.getClass().getName(), Level.ALL);
            initSocketServer(this.port, new RequestDispatcherFactory(new RequestParser(), new ResponseWriter(), this.requestHandlers, logger), logger);
        }
        this.socketServer.start();
    }

    public void initSocketServer(int port, RequestDispatcherFactory requestDispatcherFactory, Logger logger) {
        if (this.socketServer == null) {
            this.socketServer = new SocketServer(port, requestDispatcherFactory);
        }
    }

    public void stop() {
        synchronized (startUpShutdownLock) {
            if (this.socketServer != null) {
                this.socketServer.stop();
                this.socketServer = null;
            }
        }
    }

    private final Object startUpShutdownLock = new Object();
}
