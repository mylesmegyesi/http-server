package HttpServer.Responders;

import HttpServer.Request;
import HttpServer.RequestHeader;
import HttpServer.Utility.FileInfo;
import HttpServer.Utility.Mocks.FileInfoMock;
import SocketServer.Utility.Logging;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        this.logger = Logging.getLoggerAndSetLevel(this.getClass().getName(), Level.SEVERE);
        this.request = new Request("GET", "/someFile.html", "HTTP/1.1", new ArrayList<RequestHeader>(), null, null);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCanHandleWhenFileExists() throws Exception {
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.setFileExists(true);
        this.fileRequestHandler = new File(directoryToServe, fileInfo, logger);
        assertTrue("Can handle should return true when the file exists.", this.fileRequestHandler.canHandle(this.request));
    }

    @Test
    public void testCantHandleWhenFileDoesNotExists() throws Exception {
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.setFileExists(false);
        this.fileRequestHandler = new File(directoryToServe, fileInfo, logger);
        assertFalse("Can handle should return false when the file doesn't exist.", this.fileRequestHandler.canHandle(this.request));
    }

    @Test
    public void testCantHandleIfIsNotAGetRequest() throws Exception {
        Request request = new Request("POST", "/someFile.html", "HTTP/1.1", new ArrayList<RequestHeader>(), null, null);
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.setFileExists(true);
        this.fileRequestHandler = new File(directoryToServe, fileInfo, logger);
        assertFalse("Can handle should return false when the file doesn't exist.", this.fileRequestHandler.canHandle(request));
    }

    private Logger logger;
}