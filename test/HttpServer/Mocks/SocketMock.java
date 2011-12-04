package HttpServer.Mocks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Author: Myles Megyesi
 */
public class SocketMock extends Socket {

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream("".getBytes());
    }

    @Override
    public OutputStream getOutputStream() {
        return new ByteArrayOutputStream();
    }
}
