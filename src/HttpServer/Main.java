package HttpServer;

import HttpServer.Responders.Directory;
import HttpServer.Responders.File;
import HttpServer.Responders.Form;
import HttpServer.Utility.FileInfo;
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
        List<Responder> responders = new ArrayList<Responder>();
        responders.add(new Form(logger));
        responders.add(new Directory(directory, fileInfo, logger));
        responders.add(new File(directory, fileInfo, logger));

        RequestParser requestParser = new RequestParser();

        RequestHandlerFactory requestHandlerFactory = new RequestHandlerFactory(requestParser, responders, new HttpServer.Responses.NotFound(), logger);
        SocketServer.SocketServer server = new SocketServer.SocketServer(port, requestHandlerFactory, logger);
        server.startListening();
    }
}
