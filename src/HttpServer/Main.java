package HttpServer;

import HttpServer.Responders.Directory;
import HttpServer.Responders.File;
import HttpServer.Responders.NotFound;
import HttpServer.Utility.FileInfo;
import SocketServer.RequestHandlerFactory;
import SocketServer.SocketServerShutdownHandler;
import SocketServer.Utility.Logging;
import com.sun.tools.javac.resources.javac;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class Main {
    public static void main(String[] args) {
        String dir = "./public";
        int port = 8080;
        FileInfo fileInfo = new FileInfo();
        Logger logger = Logging.getLoggerAndSetLevel(Main.class.getName(), Level.ALL);

        // Setup responders
        List<SocketServer.Responder> responders = new ArrayList<SocketServer.Responder>();
        responders.add(new Directory(dir, fileInfo, logger));
        responders.add(new File(dir,fileInfo, logger));
        responders.add(new NotFound());

        RequestParser requestParser = new RequestParser();

        RequestHandlerFactory requestHandlerFactory = new RequestHandlerFactory(requestParser, responders, logger);
        SocketServer.SocketServer server = new SocketServer.SocketServer(port, requestHandlerFactory, logger);
        Runtime.getRuntime().addShutdownHook(new SocketServerShutdownHandler(server));
        server.startListening();
    }
}
