package HttpServer.Mocks;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Author: Myles Megyesi
 */
public class OutputStreamMock extends OutputStream {
    public int closeCalledCount = 0;

    public OutputStreamMock(boolean throwOnClose) {
        this.throwOnClose = throwOnClose;
    }

    @Override
    public void write(int i) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close() throws IOException {
        this.closeCalledCount++;
        if (throwOnClose) {
            throw new IOException("Whoops!");
        }
    }

    private boolean throwOnClose = false;
}
