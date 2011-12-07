package HttpServer.RequestHandlers;

import HttpServer.Exceptions.ResponseException;
import HttpServer.Headers.ConnectionClose;
import HttpServer.Headers.DateHeader;
import HttpServer.Request;
import HttpServer.RequestHandler;
import HttpServer.Response;
import HttpServer.ResponseHeader;
import HttpServer.Responses.OK;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Myles Megyesi
 */
public class Form implements RequestHandler {

    public boolean canRespond(Request request) {
        return (request.requestLine.action.equals("GET") || request.requestLine.action.equals("POST")) && request.requestLine.requestUri.equals("/form");
    }

    public Response getResponse(Request request) throws ResponseException {
        List<ResponseHeader> responseHeaders = new ArrayList<ResponseHeader>();
        responseHeaders.add(new DateHeader(new Date()));
        responseHeaders.add(new ConnectionClose());
        responseHeaders.add(new ResponseHeader("Content-Type", "text/html"));
        String html;
        if (request.requestLine.action.equals("GET")) {
            html = this.getGetHtml();
        } else {
            html = this.getPostHtml(request.body.toString(), request.requestLine.query);
        }
        return new OK(responseHeaders, new ByteArrayInputStream(html.getBytes()));
    }

    private String getGetHtml() {
        List<String> fields = new ArrayList<String>();
        fields.add(this.getFormField("Field1"));
        fields.add(this.getFormField("Field2"));
        fields.add(this.getFormField("Field3"));
        return getForm(fields);
    }

    private String getFormField(String fieldName) {
        return String.format("%s: <input type=\"text\" name=\"%s\" />", fieldName, fieldName);
    }

    private String getForm(List<String> fields) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("<form name=\"input\" action=\"/form\" method=\"POST\">"));
        for (String field : fields) {
            builder.append(field);
            builder.append("<br />");
        }
        builder.append("<input type=\"submit\" value=\"Submit\" />");
        builder.append("</form>");
        return builder.toString();
    }

    private String getPostHtml(String body, String query) {
        String stringToParse = body;
        if (!query.equals("")) {
            stringToParse = query;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<table>");
        builder.append("<tr>");
        builder.append("<td>Parameter</td>");
        builder.append("<td>Value</td>");
        builder.append("</tr>");
        String[] params = stringToParse.split("&");
        if (params.length != 0) {
            for (String param : params) {
                builder.append("<tr>");
                String[] fieldvalue = param.split("=");
                builder.append("<td>");
                builder.append(fieldvalue[0]);
                builder.append("</td>");
                builder.append("<td>");
                builder.append(fieldvalue[1]);
                builder.append("</td>");
                builder.append("</tr>");

            }
        }
        builder.append("</table>");
        return builder.toString();
    }
}
