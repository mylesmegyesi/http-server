package HttpServer.Mocks;

import HttpServer.Request;
import HttpServer.RequestHeader;
import HttpServer.RequestLine;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Author: Myles Megyesi
 */
public class RequestMock extends Request {
    public RequestMock() {
        super(new RequestLine("1", "2", "3", "4"), new ArrayList<RequestHeader>(), new ByteArrayInputStream("".getBytes()));
    }
}
