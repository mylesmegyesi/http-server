package HttpServer;

import HttpRequestParser.HttpRequest;

import java.util.Map;

/**
 * Author: Myles Megyesi
 */
public class Request {

    public String action;
    public String url;
    public String protocolVersion;
    public Map<String, String> requestHeaders;
    public Map<String, Object> parameters;

    public Request(String action, String url, String protocolVersion, Map<String, String> requestHeaders, Map<String, Object> parameters) {
        this.action = action;
        this.url = url;
        this.protocolVersion = protocolVersion;
        this.requestHeaders = requestHeaders;
        this.parameters = parameters;
    }

    public Request(HttpRequest request) {
        this.action = request.action;
        this.url = request.url;
        this.protocolVersion = request.version;
        this.requestHeaders = request.requestHeaders;
        this.parameters = request.parameters;
    }
}
