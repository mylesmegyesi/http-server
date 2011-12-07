package HttpServer.Headers;

import HttpServer.ResponseHeader;

/**
 * Author: Myles Megyesi
 */
public class ConnectionClose extends ResponseHeader{
    public ConnectionClose() {
        super("Connection", "Close");
    }
}
