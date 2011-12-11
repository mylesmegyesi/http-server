package HttpServer;


import HttpServer.Exceptions.BadRequestException;
import HttpServer.Exceptions.ResponseException;
import HttpServer.Responses.Continue;
import HttpServer.Responses.NotFound;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class RequestDispatcher implements Runnable {

    public RequestDispatcher(Socket socket, RequestParser requestParser, ResponseWriter responseWriter, List<RequestHandler> requestHandlers, Logger logger) {
        this.socket = socket;
        this.requestParser = requestParser;
        this.responseWriter = responseWriter;
        this.requestHandlers = requestHandlers;
        this.logger = logger;
    }

    public void run() {
        try {
            myRun(this.socket, this.requestParser, this.responseWriter, this.requestHandlers);
        } catch (IOException e) {
            this.logger.info(String.format("Encountered an error with the streams: %s", e.getMessage()));
        } catch (BadRequestException e) {
            this.logger.info(String.format("The request was bad: %s", e.getMessage()));
        }
    }

    public void myRun(Socket socket, RequestParser requestParser, ResponseWriter responseWriter, List<RequestHandler> requestHandlers) throws IOException, BadRequestException {
        this.logger.info(String.format("Received request from %s:%d", socket.getInetAddress(), socket.getLocalPort()));
        InputStream inputStream = this.openInputStream(socket);
        OutputStream outputStream = this.openOutputStream(socket);
        Request request = this.parseRequest(inputStream, outputStream, responseWriter, requestParser);
        handleRequest(outputStream, responseWriter, request, requestHandlers);
        closeStreams(inputStream, outputStream);
        closeSocket(socket);
    }

    public InputStream openInputStream(Socket socket) throws IOException {
        return socket.getInputStream();
    }

    public OutputStream openOutputStream(Socket socket) throws IOException {
        return socket.getOutputStream();
    }

    public Request parseRequest(InputStream inputStream, OutputStream outputStream, ResponseWriter responseWriter, RequestParser requestParser) throws IOException, BadRequestException {
        Request request = requestParser.parseRequestLineAndHeaders(inputStream);
        this.sendContinue(request, outputStream, responseWriter);
        if (actionSuggestsBody(request)) {
            request.parameters = requestParser.parseBody(inputStream, this.getContentType(request.requestHeaders));
        }
        return request;
    }

    public void sendContinue(Request request, OutputStream outputStream, ResponseWriter responseWriter) throws IOException {
        if (request.protocolVersion.equals("HTTP/1.1")) {
            responseWriter.respond(outputStream, new Continue(request.protocolVersion));
        }
    }

    public String getContentType(Map<String, String> requestHeaders) throws BadRequestException {
        if (!requestHeaders.containsKey("Content-Type")) {
            throw new BadRequestException("Content Type required for Post and Put");
        }
        return requestHeaders.get("Content-Type");
    }

    public boolean actionSuggestsBody(Request request) {
        return request.action.equals("POST") || request.action.equals("PUT");
    }

    public void handleRequest(OutputStream outputStream, ResponseWriter responseWriter, Request request, List<RequestHandler> requestHandlers) throws IOException {
        if (!this.tryRequestHandlers(outputStream, responseWriter, request, requestHandlers)) {
            responseWriter.respond(outputStream, new NotFound());
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
    private Logger logger;
}
