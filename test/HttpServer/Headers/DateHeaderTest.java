package HttpServer.Headers;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Myles Megyesi
 */
public class DateHeaderTest {
    @Test
    public void returnsADate() throws Exception {
        DateHeader dateHeader = new DateHeader();
        assertEquals("Date", dateHeader.name);
    }
}
