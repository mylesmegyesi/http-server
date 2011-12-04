package HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Author: Myles Megyesi
 */
public class Response {

    public Response(String protocolVersion, int statusCode, String reasonPhrase, List<ResponseHeader> responseHeaders, InputStream body) {
        this.protocolVersion = protocolVersion;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.responseHeaders = responseHeaders;
        this.body = body;
    }

    public void writeStatusLine(OutputStream outputStream) throws IOException {
        outputStream.write(this.protocolVersion.getBytes());
        outputStream.write(" ".getBytes());
        outputStream.write(String.valueOf(this.statusCode).getBytes());
        outputStream.write(" ".getBytes());
        outputStream.write(this.reasonPhrase.getBytes());
        outputStream.write("\r\n".getBytes());
    }

    public void writeHeaders(OutputStream outputStream) throws IOException {
        for (ResponseHeader header : this.responseHeaders) {
            outputStream.write(header.getName().getBytes());
            outputStream.write(": ".getBytes());
            outputStream.write(header.getValue().getBytes());
            outputStream.write("\r\n".getBytes());
        }
    }

    public void writeBody(OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int numRead;
        outputStream.write("\r\n".getBytes());
        while ((numRead = this.body.read(buffer)) >= 0) {
            outputStream.write(buffer, 0, numRead);
        }
        outputStream.write("\r\n".getBytes());
    }

    protected String protocolVersion;
    protected int statusCode;
    protected String reasonPhrase;
    protected List<ResponseHeader> responseHeaders;
    protected InputStream body;
}
