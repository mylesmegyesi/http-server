package HttpServer;


import HttpServer.Exceptions.BadRequestException;
import HttpServer.Exceptions.ResponseException;
import HttpServer.Utility.InputStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class RequestDispatcher implements Runnable {

    public RequestDispatcher(Socket socket, RequestParser requestParser, ResponseWriter responseWriter, List<RequestHandler> requestHandlers, Response notFound, Logger logger) {
        this.socket = socket;
        this.requestParser = requestParser;
        this.responseWriter = responseWriter;
        this.requestHandlers = requestHandlers;
        this.notFound = notFound;
        this.logger = logger;
    }

    public void run() {
        try {
            myRun(this.socket, this.requestParser, this.responseWriter, this.requestHandlers, this.notFound);
        } catch (IOException e) {
            this.logger.info(String.format("Encountered an error with the streams: %s", e.getMessage()));
        } catch (BadRequestException e) {
            this.logger.info(String.format("The request was bad: %s", e.getMessage()));
        }
    }

    public void myRun(Socket socket, RequestParser requestParser, ResponseWriter responseWriter, List<RequestHandler> requestHandlers, Response notFound) throws IOException, BadRequestException {
        this.logger.info(String.format("Received request from %s:%d", socket.getInetAddress(), socket.getLocalPort()));
        InputStream inputStream = this.openInputStream(socket);
        OutputStream outputStream = this.openOutputStream(socket);
        handleRequest(outputStream, responseWriter, requestParser.parse(inputStream, new InputStreamReader()), requestHandlers, notFound);
        closeStreams(inputStream, outputStream);
        closeSocket(socket);
    }

    public InputStream openInputStream(Socket socket) throws IOException {
        return socket.getInputStream();
    }

    public OutputStream openOutputStream(Socket socket) throws IOException {
        return socket.getOutputStream();
    }

    public void handleRequest(OutputStream outputStream, ResponseWriter responseWriter, Request request, List<RequestHandler> requestHandlers, Response notFound) throws IOException {
        if (!this.tryRequestHandlers(outputStream, responseWriter, request, requestHandlers)) {
            responseWriter.respond(outputStream, notFound);
        }
    }

    public boolean tryRequestHandlers(OutputStream outputStream, ResponseWriter responseWriter, Request request, List<RequestHandler> requestHandlers) throws IOException {
        for (RequestHandler requestHandler : requestHandlers) {
            if (this.tryRequestHandler(outputStream, responseWriter, request, requestHandler)) {
                return true;
            }
        }
        return false;
    }

    public boolean tryRequestHandler(OutputStream outputStream, ResponseWriter responseWriter, Request request, RequestHandler requestHandler) throws IOException {
        if (requestHandler.canRespond(request)) {
            try {
                responseWriter.respond(outputStream, requestHandler.getResponse(request));
                return true;
            } catch (ResponseException e) {
            }
        }
        return false;
    }

    public void closeStreams(InputStream inputStream, OutputStream outputStream) {
        try {
            closeInputStream(inputStream);
            closeOutputStream(outputStream);
        } catch (IOException e) {
        }
    }

    public void closeOutputStream(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            outputStream.close();
        }
    }

    public void closeInputStream(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
    }

    public void closeSocket(Socket socket) throws IOException {
        socket.close();
    }

    private Socket socket;
    private RequestParser requestParser;
    private ResponseWriter responseWriter;
    private List<RequestHandler> requestHandlers;
    private Response notFound;
    private Logger logger;
}
