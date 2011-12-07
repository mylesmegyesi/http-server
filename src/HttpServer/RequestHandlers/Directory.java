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

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Myles Megyesi
 */
public class Directory implements RequestHandler {

    public Directory(String directoryToServe, FileInfo fileInfo) {
        this.fileInfo = fileInfo;
        this.directoryToServe = directoryToServe;
    }

    public boolean canRespond(Request request) {
        return request.requestLine.action.equals("GET") && fileInfo.directoryExists(this.directoryToServe, request.requestLine.requestUri);
    }

    public Response getResponse(Request request) throws ResponseException {
        List<ResponseHeader> responseHeaders = new ArrayList<ResponseHeader>();
        responseHeaders.add(new DateHeader(new Date()));
        responseHeaders.add(new ResponseHeader("Content-Type", "text/html"));
        responseHeaders.add(new ConnectionClose());
        String html = this.getDirectoryHtml(request.requestLine.requestUri);
        return new OK(responseHeaders, new ByteArrayInputStream(html.getBytes()));
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
