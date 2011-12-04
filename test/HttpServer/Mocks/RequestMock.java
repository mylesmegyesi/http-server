package HttpServer.Mocks;

import HttpServer.Request;
import HttpServer.RequestHeader;

import java.util.List;

/**
 * Author: Myles Megyesi
 */
public class RequestMock extends Request{
    public RequestMock(String action, String requestUri, String protocolVersion, List<RequestHeader> requestHeaders, String body, String rawRequest) {
        super(action, requestUri, protocolVersion, requestHeaders, body, rawRequest);
    }
}
