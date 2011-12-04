package HttpServer;


import HttpServer.Utility.Date;

/**
 * Author: Myles Megyesi
 */
public class DateHeader extends ResponseHeader{
    public DateHeader(java.util.Date date) {
        super("Date: ", Date.formattedDate(date));
    }


}
