package HttpServer.Mocks;

import HttpServer.Response;
import HttpServer.ResponseHeader;

import java.util.ArrayList;

/**
 * Author: Myles Megyesi
 */
public class ResponseMock extends Response {

    public ResponseMock() {
        this.setProtocolVersion("HTTP/1.0");
        this.setStatusCode(200);
        this.setReasonPhrase("OK");
        this.setResponseHeaders(new ArrayList<ResponseHeader>());
        this.getResponseHeaders().add(new ResponseHeader("Content-Type", "text/html"));
        this.setBody("abcdefghijklmnopqrstuvwxyz1234567890abcdef");
    }

    public String getMockResponseString() {
        StringBuilder builder = new StringBuilder();
        builder.append("HTTP/1.0 200 OK\r\n");
        builder.append("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
        builder.append("Content-Type: text/html\r\n");
        builder.append("Content-Length: 42\r\n");
        builder.append("\r\n");
        builder.append("abcdefghijklmnopqrstuvwxyz1234567890abcdef");
        builder.append("\r\n");
        return builder.toString();
    }
}
