package HttpServer;


import HttpServer.Exceptions.BadRequestException;
import HttpServer.Exceptions.ResponseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class RequestHandler implements Runnable {

    public RequestHandler(Socket socket, RequestParser requestParser, List<Responder> responders, Response notFoundResponse, Logger logger) {
        this.socket = socket;
        this.requestParser = requestParser;
        this.responders = responders;
        this.notFoundResponse = notFoundResponse;
        this.logger = logger;
    }

    public void addHandler(Responder responder) {
        this.responders.add(responder);
    }

    public void run() {
        this.logger.info(String.format("Received request from %s:%d", this.socket.getInetAddress(), this.socket.getLocalPort()));
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = this.socket.getInputStream();
            outputStream = this.socket.getOutputStream();
        } catch (IOException e) {
            this.logger.severe("Could not open the input and output streams of the socket.");
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                }
            }
            return;
        }
        Request request = null;
        try {
            request = this.requestParser.parse(inputStream);
        } catch (BadRequestException e) {
            this.logger.severe("asfom");
        }
        boolean handled = false;
        for (Responder responder : this.responders) {
            if (responder.canRespond(request)) {
                try {
                    responder.respond(outputStream, request);
                    handled = true;
                    break;
                } catch (ResponseException e) {
                    this.logger.severe(String.format("%s failed to generate response: %s", responder.getClass().getName(), e.getMessage()));
                }
            }
        }
        if (!handled) {
            try {
                this.notFoundResponse.writeStatusLine(outputStream);
                outputStream.write("\r\n".getBytes());
            } catch (IOException e) {
                this.logger.severe(String.format("failed to send not found response: %s", e.getMessage()));
            }
        }
        try {
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {

        }
    }

    private Socket socket;
    private RequestParser requestParser;
    private List<Responder> responders;
    private Responder defaultHandler;
    private Response notFoundResponse;
    private Logger logger;
}
