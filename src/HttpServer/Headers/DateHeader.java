package HttpServer.Headers;


import HttpServer.ResponseHeader;
import HttpServer.Utility.Date;

/**
 * Author: Myles Megyesi
 */
public class DateHeader extends ResponseHeader {
    public DateHeader() {
        super("Date", new Date().formattedDate(new java.util.Date()));
    }


}
