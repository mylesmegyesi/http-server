package HttpServer.Responders;

import HttpServer.*;
import HttpServer.Exceptions.ResponseException;
import HttpServer.Responses.OK;
import HttpServer.Utility.FileInfo;

import javax.activation.MimetypesFileTypeMap;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class File extends Responder {

    public File(String directoryToServe, FileInfo fileInfo, Logger logger) {
        super(logger);
        this.fileInfo = fileInfo;
        this.directoryToServe = directoryToServe;
    }

    @Override
    public boolean canRespond(Request request) {
        if (!request.getAction().equals("GET")) {
            return false;
        }
        return fileInfo.fileExists(this.directoryToServe, request.getRequestUri());
    }

    @Override
    public Response getResponse(Request request) throws ResponseException {
        java.io.File fileToServe = new java.io.File(this.directoryToServe, request.getRequestUri());
        String mimeType = new MimetypesFileTypeMap().getContentType(fileToServe.getName());
        List<ResponseHeader> responseHeaders = new ArrayList<ResponseHeader>();
        responseHeaders.add(new DateHeader(new Date()));
        responseHeaders.add(new ResponseHeader("Content-Type", mimeType));
        responseHeaders.add(new ResponseHeader("Last-Modified", HttpServer.Utility.Date.formattedDate(new Date(fileToServe.lastModified()))));
        responseHeaders.add(new ResponseHeader("Content-Length", String.valueOf(fileToServe.length())));
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
