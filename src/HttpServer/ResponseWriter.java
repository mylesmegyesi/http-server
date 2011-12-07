package HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Author: Myles Megyesi
 */
public class ResponseWriter {

    public void respond(OutputStream outputStream, Response response) throws IOException {
        this.writeStatusLine(outputStream, response.statusLine);
        this.writeHeaders(outputStream, response.responseHeaders);
        this.writeBody(outputStream, response.body);
        outputStream.write("\r\n".getBytes());
    }

    public void writeStatusLine(OutputStream outputStream, ResponseStatusLine statusLine) throws IOException {
        outputStream.write(statusLine.protocolVersion.getBytes());
        outputStream.write(" ".getBytes());
        outputStream.write(String.valueOf(statusLine.statusCode).getBytes());
        outputStream.write(" ".getBytes());
        outputStream.write(statusLine.reasonPhrase.getBytes());
        outputStream.write("\r\n".getBytes());
    }

    public void writeHeaders(OutputStream outputStream, List<ResponseHeader> responseHeaders) throws IOException {
        for (ResponseHeader header : responseHeaders) {
            this.writeHeader(outputStream, header);
        }
        outputStream.write("\r\n".getBytes());
    }

    public void writeHeader(OutputStream outputStream, ResponseHeader header) throws IOException {
        outputStream.write(header.name.getBytes());
        outputStream.write(": ".getBytes());
        outputStream.write(header.value.getBytes());
        outputStream.write("\r\n".getBytes());
    }

    public void writeBody(OutputStream outputStream, InputStream body) throws IOException {
        byte[] buffer = new byte[1024];
        int numRead;
        while ((numRead = body.read(buffer)) >= 0) {
            outputStream.write(buffer, 0, numRead);
        }
        outputStream.write("\r\n".getBytes());
    }
}
