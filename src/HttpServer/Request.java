package HttpServer;

import java.io.InputStream;
import java.util.List;

/**
 * Author: Myles Megyesi
 */
public class Request {

    public RequestLine requestLine;
    public InputStream body;
    public List<RequestHeader> requestHeaders;

    public Request(RequestLine requestLine, List<RequestHeader> requestHeaders, InputStream body) {
        this.requestLine = requestLine;
        this.requestHeaders = requestHeaders;
        this.body = body;
    }
}
