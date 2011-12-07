package HttpServer;

/**
 * Author: Myles Megyesi
 */
public class ResponseStatusLine {

    public String protocolVersion;
    public int statusCode;
    public String reasonPhrase;

    public ResponseStatusLine(String protocolVersion, int statusCode, String reasonPhrase) {
        this.protocolVersion = protocolVersion;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

}
