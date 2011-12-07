package HttpServer;

import HttpServer.Mocks.SocketServerMock;
import HttpServer.Responses.NotFound;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Author: Myles Megyesi
 */
public class HttpServerTest {

    @Test
    public void firstConstructor() throws Exception {
        HttpServer httpServer = new HttpServer(0);
        assertEquals(0, httpServer.port);
    }

    @Test
    public void secondConstructor() throws Exception {
        List<RequestHandler> requestHandlers = new ArrayList<RequestHandler>();
        HttpServer httpServer = new HttpServer(0, requestHandlers);
        assertEquals(0, httpServer.port);
        assertEquals(requestHandlers, httpServer.requestHandlers);
    }

    @Test
    public void startCallsTheSocketServerStart() throws Exception {
        HttpServer httpServer = new HttpServer(0);
        SocketServerMock socketServerMock = new SocketServerMock();
        httpServer.socketServer = socketServerMock;
        httpServer.start();
        assertEquals(1, socketServerMock.startCalledCount);
    }

    @Test
    public void stopCallsTheSocketServerStop() throws Exception {
        HttpServer httpServer = new HttpServer(0);
        SocketServerMock socketServerMock = new SocketServerMock();
        httpServer.socketServer = socketServerMock;
        httpServer.stop();
        assertEquals(1, socketServerMock.stopCalledCount);
    }

    @Test
    public void initSocketServerCreatesANewServerIfNull() throws Exception {
        HttpServer httpServer = new HttpServer(0);
        Logger logger = Logger.getLogger(this.getClass().getName());
        httpServer.initSocketServer(httpServer.port, new RequestDispatcherFactory(new RequestParser(), new ResponseWriter(), new ArrayList<RequestHandler>(), new NotFound(), logger), logger);
        assertTrue(httpServer.socketServer != null);
    }

    @Test
    public void initSocketServerDoesntCreateANewServerIfNotNull() throws Exception {
        HttpServer httpServer = new HttpServer(0);
        SocketServerMock socketServer = new SocketServerMock();
        httpServer.socketServer = socketServer;
        Logger logger = Logger.getLogger(this.getClass().getName());
        httpServer.initSocketServer(httpServer.port, new RequestDispatcherFactory(new RequestParser(), new ResponseWriter(), new ArrayList<RequestHandler>(), new NotFound(), logger), logger);
        assertTrue(httpServer.socketServer == socketServer);
    }
}
