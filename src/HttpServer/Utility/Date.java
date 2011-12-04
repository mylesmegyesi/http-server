package HttpServer.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Author: Myles Megyesi
 */
public class Date {

    public static String formattedDate(java.util.Date date) {
        return dateFormat.format(date);
    }

    private static DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");

}
