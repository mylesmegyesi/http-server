package HttpServer.Mocks;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author: Myles Megyesi
 */
public class InputStreamMock extends InputStream {

    public int closeCalledCount = 0;

    public InputStreamMock(boolean throwOnClose) {
        this.throwOnClose = throwOnClose;
    }

    @Override
    public int read() throws IOException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
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
