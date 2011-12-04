package HttpServer.Responses;

import HttpServer.Response;
import HttpServer.ResponseHeader;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Author: Myles Megyesi
 */
public class NotFound extends Response {

    public NotFound() {
        super("HTTP/1.1", 404, "Not Found", new ArrayList<ResponseHeader>(), new ByteArrayInputStream("404: Resouce Not Found".getBytes()));
    }
}
