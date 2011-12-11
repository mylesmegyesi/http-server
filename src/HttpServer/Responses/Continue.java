package HttpServer.Responses;

import HttpServer.Response;
import HttpServer.ResponseHeader;
import HttpServer.ResponseStatusLine;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Author: Myles Megyesi
 */
public class Continue extends Response {
    public Continue(String version) {
        super(new ResponseStatusLine(version, 100, "Continue"), new ArrayList<ResponseHeader>(), new ByteArrayInputStream("".getBytes()));
    }
}
