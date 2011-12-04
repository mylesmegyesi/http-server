package HttpServer;

import HttpServer.Responders.Directory;
import HttpServer.Responders.File;
import HttpServer.Responders.NotFound;
import HttpServer.Utility.FileInfo;
import SocketServer.RequestHandlerFactory;
import SocketServer.SocketServerShutdownHandler;
import SocketServer.Utility.Logging;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class Main {


    private static void printUsage() {
        System.out.println("Usage: -p <port> -d <directory>");
    }

    public static void main(String[] args) {
        int port = 8080;
        String directory = ".";

        if (args.length == 4) {
           port = Integer.parseInt(args[1]);
            directory = args[3];
        }

        FileInfo fileInfo = new FileInfo();
        Logger logger = Logging.getLoggerAndSetLevel(Main.class.getName(), Level.ALL);

        // Setup responders
        List<SocketServer.Responder> responders = new ArrayList<SocketServer.Responder>();
        responders.add(new Directory(directory, fileInfo, logger));
        responders.add(new File(directory, fileInfo, logger));
        responders.add(new NotFound());

        RequestParser requestParser = new RequestParser();

        RequestHandlerFactory requestHandlerFactory = new RequestHandlerFactory(requestParser, responders, logger);
        SocketServer.SocketServer server = new SocketServer.SocketServer(port, requestHandlerFactory, logger);
        Runtime.getRuntime().addShutdownHook(new SocketServerShutdownHandler(server));
        server.startListening();
    }
}
