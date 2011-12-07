package HttpServer.Mocks;

import HttpServer.ResponseWriter;
import HttpServer.Response;

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
