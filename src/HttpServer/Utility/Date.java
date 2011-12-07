package HttpServer.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Author: Myles Megyesi
 */
public class Date {

    public DateFormat dateFormat;

    public Date() {
        this.dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    }

    public Date(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String formattedDate(java.util.Date date) {
        return this.dateFormat.format(date);
    }

}
