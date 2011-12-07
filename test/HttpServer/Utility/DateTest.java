package HttpServer.Utility;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Myles Megyesi
 */
public class DateTest {
    @Test
    public void returnsADate() throws Exception {
        DateFormat dateFormat = new SimpleDateFormat();
        Date dateUtil = new Date(dateFormat);
        java.util.Date date = new java.util.Date();
        assertEquals(dateFormat.format(date), dateUtil.formattedDate(date));
    }
}
