package HttpServer.Mocks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Author: Myles Megyesi
 */
public class SocketMock extends Socket {

    public int getInputStreamCalledCount = 0;
    public int getOutputStreamCalledCount = 0;
    public int closeCalledCount = 0;
    public boolean throwOnGetInputStream = false;
    public boolean throwOnGetOutputStream = false;
    public InputStream inputStream;
    public OutputStream outputStream;

    public SocketMock(boolean throwOnGetInputStream, boolean throwOnGetOutputStream) {
        this.throwOnGetInputStream = throwOnGetInputStream;
        this.throwOnGetOutputStream = throwOnGetOutputStream;
        this.inputStream = new InputStreamMock(false);
        this.outputStream = new OutputStreamMock(false);
    }

    public SocketMock(boolean throwOnGetInputStream, boolean throwOnGetOutputStream, InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        this.getInputStreamCalledCount++;
        if (this.throwOnGetInputStream) {
            throw new IOException();
        }
        return this.inputStream;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        this.getOutputStreamCalledCount++;
        if (this.throwOnGetOutputStream) {
            throw new IOException();
        }
        return this.outputStream;
    }

    @Override
    public void close() {
        this.closeCalledCount++;
    }
}
