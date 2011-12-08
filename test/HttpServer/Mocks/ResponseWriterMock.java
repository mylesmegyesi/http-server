package HttpServer.Mocks;

import HttpServer.Response;
import HttpServer.ResponseWriter;

import java.io.OutputStream;

/**
 * Author: Myles Megyesi
 */
public class ResponseWriterMock extends ResponseWriter {

    public int respondCallCount = 0;

    @Override
    public void respond(OutputStream outputStream, Response response) {
        this.respondCallCount++;
    }
}
