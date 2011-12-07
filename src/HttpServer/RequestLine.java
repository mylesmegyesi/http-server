package HttpServer;

/**
 * Author: Myles Megyesi
 */
public class RequestLine {

    public String action;
    public String requestUri;
    public String query;
    public String protocolVersion;

    public RequestLine(String action, String requestUri, String query, String protocolVersion) {
        this.action = action;
        this.requestUri = requestUri;
        this.query = query;
        this.protocolVersion = protocolVersion;
    }

}
