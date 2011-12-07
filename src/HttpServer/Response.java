package HttpServer;

import java.io.InputStream;
import java.util.List;

/**
 * Author: Myles Megyesi
 */
public class Response {

    public ResponseStatusLine statusLine;
    public List<ResponseHeader> responseHeaders;
    public InputStream body;

    public Response(ResponseStatusLine statusLine, List<ResponseHeader> responseHeaders, InputStream body) {
        this.statusLine = statusLine;
        this.responseHeaders = responseHeaders;
        this.body = body;
    }
}
