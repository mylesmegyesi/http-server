package HttpServer.Responses;

import HttpServer.Response;
import HttpServer.ResponseHeader;
import HttpServer.ResponseStatusLine;

import java.io.InputStream;
import java.util.List;

/**
 * Author: Myles Megyesi
 */
public class OK extends Response {
    public OK(List<ResponseHeader> responseHeaders, InputStream body) {
        super(new ResponseStatusLine("HTTP/1.1", 200, "OK"), responseHeaders, body);
    }
}
