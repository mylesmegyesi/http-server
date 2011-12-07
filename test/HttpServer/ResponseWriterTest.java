package HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Myles Megyesi
 */
public class ResponseWriterTest {

    ResponseWriter responseWriter;
    OutputStream outputStream;

    @Before
    public void setUp() throws Exception {
        this.responseWriter = new ResponseWriter();
        this.outputStream = new ByteArrayOutputStream();
    }

    @After
    public void tearDown() throws Exception {
        this.responseWriter = null;
        this.outputStream.close();
        this.outputStream = null;
    }

    @Test
    public void writesStatusLine() throws Exception {
        responseWriter.writeStatusLine(this.outputStream, new ResponseStatusLine("HTTP/1.1", 200, "OK"));
        assertEquals("Builds the status line incorrectly.", "HTTP/1.1 200 OK\r\n", this.outputStream.toString());
    }

    @Test
    public void writesAHeader() throws Exception {
        responseWriter.writeHeader(this.outputStream, new ResponseHeader("Content-Type", "text/html"));
        assertEquals("Builds the headers incorrectly.", "Content-Type: text/html\r\n", this.outputStream.toString());
    }

    @Test
    public void writesHeaders() throws Exception {
        List<ResponseHeader> responseHeaders = new ArrayList<ResponseHeader>();
        responseHeaders.add(new ResponseHeader("Content-Type", "text/html"));
        responseHeaders.add(new ResponseHeader("Content-Length", "42"));
        responseWriter.writeHeaders(this.outputStream, responseHeaders);
        assertEquals("Builds the headers incorrectly.", "Content-Type: text/html\r\nContent-Length: 42\r\n\r\n", this.outputStream.toString());
    }

    @Test
    public void writesBody() throws Exception {
        responseWriter.writeBody(this.outputStream, new ByteArrayInputStream("response".getBytes()));
        assertEquals("Builds the body incorrectly.", "response\r\n", this.outputStream.toString());
    }

    @Test
    public void writesAll() throws Exception {
        List<ResponseHeader> responseHeaders = new ArrayList<ResponseHeader>();
        responseHeaders.add(new ResponseHeader("Content-Type", "text/html"));
        responseHeaders.add(new ResponseHeader("Content-Length", "42"));
        Response response = new Response(new ResponseStatusLine("HTTP/1.1", 200, "OK"), responseHeaders, new ByteArrayInputStream("response".getBytes()));
        responseWriter.respond(this.outputStream, response);
        assertEquals("Builds the response incorrectly.", "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: 42\r\n\r\nresponse\r\n\r\n", this.outputStream.toString());
    }
}
