package HttpServer.RequestHandlers;

import HttpServer.Request;
import HttpServer.RequestHeader;
import HttpServer.RequestLine;
import HttpServer.Utility.Mocks.FileInfoMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Author: Myles Megyesi
 */
public class FileTest {

    File fileRequestHandler;
    Request request;
    String directoryToServe = ".";

    @Before
    public void setUp() throws Exception {
        this.request = new Request(new RequestLine("GET", "/someFile.html", "", "HTTP/1.1"), new ArrayList<RequestHeader>(), null);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCanHandleWhenFileExists() throws Exception {
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.setFileExists(true);
        this.fileRequestHandler = new File(directoryToServe, fileInfo);
        assertTrue("Can handle should return true when the file exists.", this.fileRequestHandler.canRespond(this.request));
    }

    @Test
    public void testCantHandleWhenFileDoesNotExists() throws Exception {
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.setFileExists(false);
        this.fileRequestHandler = new File(directoryToServe, fileInfo);
        assertFalse("Can handle should return false when the file doesn't exist.", this.fileRequestHandler.canRespond(this.request));
    }

    @Test
    public void testCantHandleIfIsNotAGetRequest() throws Exception {
        Request request = new Request(new RequestLine("POST", "/someFile.html", "", "HTTP/1.1"), new ArrayList<RequestHeader>(), null);
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.setFileExists(true);
        this.fileRequestHandler = new File(directoryToServe, fileInfo);
        assertFalse("Can handle should return false when the file doesn't exist.", this.fileRequestHandler.canRespond(request));
    }

}
