package HttpServer.Mocks;

import HttpRequestParser.HttpRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestMock extends HttpRequest{
    public HttpRequestMock() {
        super("", "", "", new HashMap<String, String>(), new HashMap<String, Object>());
    }
}
