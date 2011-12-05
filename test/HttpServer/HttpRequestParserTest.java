package HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Author: Myles Megyesi
 */
public class HttpRequestParserTest {

    RequestParser requestParser;

    @Before
    public void setUp() throws Exception {
        this.requestParser = new RequestParser();
    }

    @After
    public void tearDown() throws Exception {
        this.requestParser = null;
    }

    @Test
    public void parsesTheAction() throws Exception {
        Request request = this.requestParser.parse(new ByteArrayInputStream("GET /path/file.html HTTP/1.0".getBytes()));
        assertEquals("GET", request.getAction());
    }

    @Test
    public void parsesTheUri() throws Exception {
        Request request = this.requestParser.parse(new ByteArrayInputStream("GET /path/file.html HTTP/1.0".getBytes()));
        assertEquals("/path/file.html", request.getRequestUri());
    }

    @Test
    public void parsesTheUriWithQuery() throws Exception {
        Request request = this.requestParser.parse(new ByteArrayInputStream("GET /path/file.html?field1=value HTTP/1.0".getBytes()));
        assertEquals("/path/file.html", request.getRequestUri());
    }

    @Test
    public void parsesTheQueryString() throws Exception {
        Request request = this.requestParser.parse(new ByteArrayInputStream("GET /path/file.html?field1=value HTTP/1.0".getBytes()));
        assertEquals("field1=value", request.getQuery());
    }

    @Test
    public void parsesTheProtocolVersion() throws Exception {
        Request request = this.requestParser.parse(new ByteArrayInputStream("GET /path/file.html HTTP/1.0".getBytes()));
        assertEquals("HTTP/1.0", request.getProtocolVersion());
    }

    @Test
    public void parsesTheHeaders() throws Exception {
        Request request = this.requestParser.parse(new ByteArrayInputStream(fakePostRequest().getBytes()));
        assertTrue("Headers aren't parsed correctly.", headersAreEqual(headersForFakePostRequest(), request.getRequestHeaders()));
    }

    @Test
    public void parsesTheBody() throws Exception {
        Request request = this.requestParser.parse(new ByteArrayInputStream(fakePostRequest().getBytes()));
        assertEquals("Body isn't parsed correctly.", rawPostForFakePostRequest(), request.getBody());
    }

    @Test
    public void returnsTheRawRequest() throws Exception {
        Request request = this.requestParser.parse(new ByteArrayInputStream(fakePostRequest().getBytes()));
        assertEquals("Raw request isn't returned in tact.", fakePostRequest(), request.getRawRequest());
    }


    private boolean headersAreEqual(List<RequestHeader> headers1, List<RequestHeader> headers2) {
        if (headers1.size() != headers2.size()) {
            return false;
        }
        for (int i = 0; i < headers1.size(); i++) {
            RequestHeader header1 = headers1.get(i);
            RequestHeader header2 = headers2.get(i);
            if ((!header1.getName().equals(header2.getName())) || (!header1.getValue().equals(header2.getValue()))) {
                return false;
            }
        }
        return true;
    }

    private List<RequestHeader> headersForFakePostRequest() {
        ArrayList<RequestHeader> headers = new ArrayList<RequestHeader>();
        headers.add(new RequestHeader("Host", "localhost:8080"));
        headers.add(new RequestHeader("From", "frog@jmarshall.com"));
        headers.add(new RequestHeader("User-Agent", "HTTPTool/1.0"));
        headers.add(new RequestHeader("Content-Type", "application/x-www-form-urlencoded"));
        headers.add(new RequestHeader("Content-Length", "32"));
        return headers;
    }

    private String rawPostForFakePostRequest() {
        return "home=Cosby&favorite+flavor=flies";
    }

    private String fakePostRequest() {
        return "POST /path/script.cgi HTTP/1.0\n" +
                "Host: localhost:8080\n" +
                "From: frog@jmarshall.com\n" +
                "User-Agent: HTTPTool/1.0\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "Content-Length: 32\n" +
                "\n" +
                "home=Cosby&favorite+flavor=flies";
    }
}

