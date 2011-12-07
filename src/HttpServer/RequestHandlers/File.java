package HttpServer.RequestHandlers;

import HttpServer.Exceptions.ResponseException;
import HttpServer.Headers.ConnectionClose;
import HttpServer.Headers.DateHeader;
import HttpServer.Request;
import HttpServer.RequestHandler;
import HttpServer.Response;
import HttpServer.ResponseHeader;
import HttpServer.Responses.OK;
import HttpServer.Utility.FileInfo;

import javax.activation.MimetypesFileTypeMap;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Myles Megyesi
 */
public class File implements RequestHandler {

    public File(String directoryToServe, FileInfo fileInfo) {
        this.fileInfo = fileInfo;
        this.directoryToServe = directoryToServe;
    }

    public boolean canRespond(Request request) {
        if (!request.requestLine.action.equals("GET")) {
            return false;
        }
        return fileInfo.fileExists(this.directoryToServe, request.requestLine.requestUri);
    }

    public Response getResponse(Request request) throws ResponseException {
        java.io.File fileToServe = new java.io.File(this.directoryToServe, request.requestLine.requestUri);
        String mimeType = new MimetypesFileTypeMap().getContentType(fileToServe.getName());
        List<ResponseHeader> responseHeaders = new ArrayList<ResponseHeader>();
        responseHeaders.add(new DateHeader(new Date()));
        responseHeaders.add(new ResponseHeader("Content-Type", mimeType));
        responseHeaders.add(new ResponseHeader("Last-Modified", HttpServer.Utility.Date.formattedDate(new Date(fileToServe.lastModified()))));
        responseHeaders.add(new ResponseHeader("Content-Length", String.valueOf(fileToServe.length())));
        responseHeaders.add(new ConnectionClose());
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(fileToServe);
        } catch (FileNotFoundException e) {
            throw new ResponseException(e.getMessage());
        }
        return new OK(responseHeaders, fileInputStream);
    }

    private FileInfo fileInfo;
    private String directoryToServe;
}
