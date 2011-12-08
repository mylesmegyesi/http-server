package HttpServer.Responses;

import HttpServer.Response;
import HttpServer.ResponseHeader;
import HttpServer.ResponseStatusLine;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Author: Myles Megyesi
 */
public class NotFound extends Response {

    public NotFound() {
        super(new ResponseStatusLine("HTTP/1.1", 404, "Not Found"), new ArrayList<ResponseHeader>(), new ByteArrayInputStream("404: Resource Not Found".getBytes()));
    }
}
