package HttpServer;

import HttpServer.RequestHandlers.Directory;
import HttpServer.RequestHandlers.File;
import HttpServer.RequestHandlers.Form;
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
        List<RequestHandler> requestHandlers = new ArrayList<RequestHandler>();
        requestHandlers.add(new Form());
        requestHandlers.add(new Directory(directory, fileInfo));
        requestHandlers.add(new File(directory, fileInfo));

        RequestParser requestParser = new RequestParser();

        RequestDispatcherFactory requestDispatcherFactory = new RequestDispatcherFactory(requestParser, new ResponseWriter(), requestHandlers, new HttpServer.Responses.NotFound(), logger);
        SocketServer.SocketServer server = new SocketServer.SocketServer(port, requestDispatcherFactory, logger);
        server.startListening();
    }
}
