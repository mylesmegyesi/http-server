package HttpServer.Responders;

import HttpServer.Request;
import HttpServer.Responder;
import HttpServer.Response;
import HttpServer.ResponseHeader;
import HttpServer.Utility.FileInfo;
import SocketServer.Exceptions.ResponseException;

import javax.activation.MimetypesFileTypeMap;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class File extends Responder {

    public File(String directoryToServe, FileInfo fileInfo, Logger logger) {
        this.fileInfo = fileInfo;
        this.directoryToServe = directoryToServe;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    @Override
    public boolean canHandle(Request request) {
        if (!request.getAction().equals("GET")) {
            return false;
        }
        return fileInfo.fileExists(this.directoryToServe, request.getRequestUri());
    }

    @Override
    public Response getResponse(Request request) throws ResponseException {
        Response response = new Response("HTTP/1.1", 200, "OK", new ArrayList<ResponseHeader>(), null);
        java.io.File fileToServe = new java.io.File(this.directoryToServe, request.getRequestUri());
        String mimeType = new MimetypesFileTypeMap().getContentType(fileToServe.getName());
        response.addResponseHeader(new ResponseHeader("Content-Type", mimeType));
        response.addResponseHeader(new ResponseHeader("Last-Modified", response.getFormattedDate(new Date(fileToServe.lastModified()))));
        FileReader reader = null;
        try {
            reader = new FileReader(fileToServe);
        } catch (FileNotFoundException e) {
            throw new ResponseException(e.getMessage());
        }
        StringBuilder builder = new StringBuilder();
        try {
            while (reader.ready()) {
                builder.append((char) reader.read());
            }
        } catch (IOException e) {
            throw new ResponseException(e.getMessage());
        }
        response.setBody(builder.toString());
        return response;
    }

    private FileInfo fileInfo;
    private String directoryToServe;
}
