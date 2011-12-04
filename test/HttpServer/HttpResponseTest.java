package HttpServer;

import HttpServer.Responses.OK;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Myles Megyesi
 */
public class HttpResponseTest {

    @Test
    public void writesStatusLine() throws Exception {
        Response response = new OK(null, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        response.writeStatusLine(outputStream);
        assertEquals("Builds status line incorrectly.", "HTTP/1.1 200 OK\r\n", outputStream.toString());
    }

    @Test
    public void writesHeaders() throws Exception {
        List<ResponseHeader> responseHeaders = new ArrayList<ResponseHeader>();
        responseHeaders.add(new ResponseHeader("Content-Type", "text/html"));
        responseHeaders.add(new ResponseHeader("Content-Length", "42"));
        Response response = new OK(responseHeaders, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        response.writeHeaders(outputStream);
        assertEquals("Builds status line incorrectly.", "Content-Type: text/html\r\nContent-Length: 42\r\n", outputStream.toString());
    }

    @Test
    public void writesBody() throws Exception {
        Response response = new OK(null, new ByteArrayInputStream("response".getBytes()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        response.writeBody(outputStream);
        assertEquals("Builds status line incorrectly.", "\r\nresponse\r\n", outputStream.toString());
    }
}
