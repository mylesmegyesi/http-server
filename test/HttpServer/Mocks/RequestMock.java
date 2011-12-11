package HttpServer.Mocks;

import HttpServer.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Myles Megyesi
 */
public class RequestMock extends Request {

    public RequestMock(String action, String version, Map<String, String> requestHeaders) {
        super(action, "", version, requestHeaders, new HashMap<String, Object>());
    }

    public RequestMock(String version) {
        super("", "", version, new HashMap<String, String>(), new HashMap<String, Object>());
    }

    public RequestMock() {
        super("", "", "", new HashMap<String, String>(), new HashMap<String, Object>());
    }
}
