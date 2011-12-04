package HttpServer.Responders;

import HttpServer.Request;
import HttpServer.Responder;
import HttpServer.Response;
import HttpServer.ResponseHeader;
import HttpServer.Utility.FileInfo;
import SocketServer.Exceptions.ResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class Directory extends Responder {

    public Directory(String directoryToServe, FileInfo fileInfo, Logger logger) {
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
        return fileInfo.directoryExists(this.directoryToServe, request.getRequestUri());
    }

    @Override
    public Response getResponse(Request request) throws ResponseException {
        Response response = new Response("HTTP/1.1", 200, "OK", new ArrayList<ResponseHeader>(), null);
        response.addResponseHeader(new ResponseHeader("Content-Type", "text/html"));
        response.setBody(this.getDirectoryHtml(request.getRequestUri()));
        return response;
    }

    private String getDirectoryHtml(String directory) {
        List<String> rows = this.getDirectoryRows(directory);
        return this.createTable(rows);
    }

    private List<String> getDirectoryRows(String directory) {
        List<String> rows = new ArrayList<String>();
        for (String entry : this.fileInfo.getEntries(this.directoryToServe, directory)) {
            rows.add(this.getRow(directory, entry));
        }
        return rows;
    }

    private String getRow(String parent, String entry) {
        StringBuilder builder = new StringBuilder();
        builder.append("<tr>");
        builder.append("<td>");
        builder.append(this.getLink(parent, entry));
        builder.append("</td>");
        builder.append("</tr>");
        return builder.toString();
    }

    private String getLink(String parent, String entry) {
        return String.format("<a href=\"%s\">%s</a>", this.fileInfo.getRelativePath(parent, entry), entry);
    }

    private String createTable(List<String> rows) {
        StringBuilder builder = new StringBuilder();
        builder.append("<table>");
        for (String row : rows) {
            builder.append(row);
        }
        builder.append("</table>");
        return builder.toString();
    }

    private FileInfo fileInfo;
    private String directoryToServe;
}
