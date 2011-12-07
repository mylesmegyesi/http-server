package HttpServer;

import HttpServer.Mocks.*;
import HttpServer.Responses.NotFound;
import SocketServer.Utility.Logging;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static junit.framework.Assert.*;

/**
 * Author: Myles Megyesi
 */
public class RequestDispatcherTest {

    RequestDispatcher requestDispatcher;
    SocketMock socket;
    RequestParserMock requestParserMock;
    ResponseWriterMock responder;
    Response notFoundResponse;
    RequestMock request;
    List<RequestHandler> requestHandlers;
    Logger logger;

    @org.junit.Before
    public void setUp() throws Exception {
        this.logger = Logging.getLoggerAndSetLevel(this.getClass().getName(), Level.OFF);
        this.requestParserMock = new RequestParserMock(false);
        this.responder = new ResponseWriterMock();
        this.socket = new SocketMock(false, false);
        this.notFoundResponse = new NotFound();
        this.request = new RequestMock();
        this.requestHandlers = new ArrayList<RequestHandler>();
        this.requestDispatcher = new RequestDispatcher(this.socket, this.requestParserMock, this.responder, this.requestHandlers, this.notFoundResponse, this.logger);
    }

    @org.junit.After
    public void tearDown() throws Exception {
        this.logger = null;
        this.requestParserMock = null;
        this.responder = null;
        this.socket = null;
        this.request = null;
        this.requestDispatcher = null;
        this.requestHandlers = null;
    }

    @Test
    public void opensTheInputStream() throws Exception {
        this.requestDispatcher.openInputStream(this.socket);
        assertEquals("The socket's input stream is not being opened", 1, this.socket.getInputStreamCalledCount);
    }

    @Test
    public void opensTheOutputStream() throws Exception {
        this.requestDispatcher.openOutputStream(this.socket);
        assertEquals("The socket's output stream is not being opened", 1, this.socket.getOutputStreamCalledCount);
    }

    @Test
    public void closesTheSocket() throws Exception {
        this.requestDispatcher.closeSocket(this.socket);
        assertEquals("The socket is not closed", 1, this.socket.closeCalledCount);
    }

    @Test
    public void returnsFalseIfHandlerCantHandle() throws Exception {
        RequestHandlerMock requestHandlerMock = new RequestHandlerMock(false, false);
        assertFalse("Doesn't interpret the handlers abilities correctly", this.requestDispatcher.tryRequestHandler(this.socket.getOutputStream(), this.responder, this.request, requestHandlerMock));
    }

    @Test
    public void callsGetResponseIfCanRespond() throws Exception {
        RequestHandlerMock requestHandlerMock = new RequestHandlerMock(true, false);
        this.requestDispatcher.tryRequestHandler(this.socket.getOutputStream(), this.responder, this.request, requestHandlerMock);
        assertEquals("Doesn't interpret the handlers abilities correctly", 1, requestHandlerMock.getResponseCalledCount);
    }

    @Test
    public void returnsFalseIfResponseHandlerThrowsAResponseException() throws Exception {
        RequestHandlerMock requestHandlerMock = new RequestHandlerMock(true, true);
        assertFalse("Doesn't interpret the handlers abilities correctly", this.requestDispatcher.tryRequestHandler(this.socket.getOutputStream(), this.responder, this.request, requestHandlerMock));
    }

    @Test
    public void callsRespondIfGetResponseDoesntThrow() throws Exception {
        RequestHandlerMock requestHandlerMock = new RequestHandlerMock(true, false);
        this.requestDispatcher.tryRequestHandler(this.socket.getOutputStream(), this.responder, this.request, requestHandlerMock);
        assertEquals("Doesn't call respond", 1, this.responder.respondCallCount);
    }

    @Test
    public void cantTryHandlersIfThereAreNoRequestHandlers() throws Exception {
        assertFalse("It think there is some handler.", this.requestDispatcher.tryRequestHandlers(this.socket.getOutputStream(), this.responder, this.request, this.requestHandlers));
    }

    @Test
    public void triesAllTheHandlers() throws Exception {
        RequestHandlerMock requestHandlerMock1 = new RequestHandlerMock(false, false);
        RequestHandlerMock requestHandlerMock2 = new RequestHandlerMock(false, false);
        this.requestHandlers.add(requestHandlerMock1);
        this.requestHandlers.add(requestHandlerMock2);
        this.requestDispatcher.tryRequestHandlers(this.socket.getOutputStream(), this.responder, this.request, this.requestHandlers);
        assertEquals("Doesn't try the first request handler.", 1, requestHandlerMock1.canHandleCalledCount);
        assertEquals("Doesn't try the second request handler.", 1, requestHandlerMock2.canHandleCalledCount);
    }

    @Test
    public void doesntTryTheSecondHandlerIfTheFirstHandles() throws Exception {
        RequestHandlerMock requestHandlerMock1 = new RequestHandlerMock(true, false);
        RequestHandlerMock requestHandlerMock2 = new RequestHandlerMock(false, false);
        this.requestHandlers.add(requestHandlerMock1);
        this.requestHandlers.add(requestHandlerMock2);
        this.requestDispatcher.tryRequestHandlers(this.socket.getOutputStream(), this.responder, this.request, this.requestHandlers);
        assertEquals("Doesn't try the first request handler.", 1, requestHandlerMock1.canHandleCalledCount);
        assertEquals("Doesn't try the second request handler.", 0, requestHandlerMock2.canHandleCalledCount);
    }

    @Test
    public void respondIsCalledIfNoRequestHandlersPresent() throws Exception {
        this.requestDispatcher.handleRequest(this.socket.getOutputStream(), this.responder, this.request, this.requestHandlers, this.notFoundResponse);
        assertEquals("Doesn't call respond.", 1, this.responder.respondCallCount);
    }

    @Test
    public void closingStreamsDoesNotThrowAnException() throws Exception {
        InputStreamMock inputStreamMock = new InputStreamMock(true);
        try {
            this.requestDispatcher.closeStreams(inputStreamMock, null);
        } catch (Exception e) {
            fail("It still throws.");
        }
    }

    @Test
    public void closesTheInputStream() throws Exception {
        InputStreamMock inputStreamMock = new InputStreamMock(false);
        this.requestDispatcher.closeInputStream(inputStreamMock);
        assertEquals("The socket is not closed", 1, inputStreamMock.closeCalledCount);
    }

    @Test
    public void closesTheOutputStream() throws Exception {
        OutputStreamMock outputStreamMock = new OutputStreamMock(false);
        this.requestDispatcher.closeOutputStream(outputStreamMock);
        assertEquals("The socket is not closed", 1, outputStreamMock.closeCalledCount);
    }

    @Test
    public void doesNotThrowAnExceptionIfInputStreamIsNull() throws Exception {
        try {
            this.requestDispatcher.closeInputStream(null);
        } catch (Exception e) {
            fail("Throws is input stream is null");
        }
    }

    @Test
    public void doesNotThrowAnExceptionIfOutputStreamIsNull() throws Exception {
        try {
            this.requestDispatcher.closeOutputStream(null);
        } catch (Exception e) {
            fail("Throws is input stream is null");
        }
    }

    @Test
    public void runCallsTheRequestParser() throws Exception {
        this.requestDispatcher.myRun(this.socket, this.requestParserMock, this.responder, this.requestHandlers, this.notFoundResponse);
        assertEquals("The request parser was not called.", 1, this.requestParserMock.getCalledCount());
    }

    @Test
    public void runClosesTheSocket() throws Exception {
        this.requestDispatcher.myRun(this.socket, this.requestParserMock, this.responder, this.requestHandlers, this.notFoundResponse);
        assertEquals("The socket is not closed", 1, this.socket.closeCalledCount);
    }

    @Test
    public void runClosesTheStreams() throws Exception {
        InputStreamMock inputStreamMock = new InputStreamMock(false);
        OutputStreamMock outputStreamMock = new OutputStreamMock(false);
        SocketMock socketMock = new SocketMock(false, false, inputStreamMock, outputStreamMock);
        this.requestDispatcher.myRun(socketMock, this.requestParserMock, this.responder, this.requestHandlers, this.notFoundResponse);
        assertEquals("The input stream is not closed", 1, inputStreamMock.closeCalledCount);
        assertEquals("The output stream is not closed", 1, outputStreamMock.closeCalledCount);
    }

    @Test
    public void threadRunEatsAnIoException() throws Exception {
        SocketMock socketMock = new SocketMock(true, false);
        try {
            this.requestDispatcher = new RequestDispatcher(socketMock, this.requestParserMock, this.responder, this.requestHandlers, this.notFoundResponse, this.logger);
            Thread thread = new Thread(this.requestDispatcher);
            thread.start();
            thread.join();
        } catch (Exception e) {
            fail("Doesn't eat it.");
        }
    }

    @Test
    public void threadRunEatsABadRequest() throws Exception {
        this.requestParserMock = new RequestParserMock(true);
        try {
            this.requestDispatcher = new RequestDispatcher(socket, this.requestParserMock, this.responder, this.requestHandlers, this.notFoundResponse, this.logger);
            Thread thread = new Thread(this.requestDispatcher);
            thread.start();
            thread.join();
        } catch (Exception e) {
            fail("Doesn't eat it.");
        }
    }

}
