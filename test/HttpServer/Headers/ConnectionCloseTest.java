package HttpServer.Headers;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Myles Megyesi
 */
public class ConnectionCloseTest {
    @Test
    public void setsTheHeader() throws Exception {
        ConnectionClose close = new ConnectionClose();
        assertEquals("Connection", close.name);
        assertEquals("Close", close.value);
    }

}
