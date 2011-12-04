package HttpServer.Responders;

import HttpServer.Request;
import HttpServer.RequestHeader;
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
public class DirectoryTest {

    Directory directoryRequestHandler;
    Request request;
    String directoryToServe = ".";

    @Before
    public void setUp() throws Exception {
        this.logger = Logging.getLoggerAndSetLevel(this.getClass().getName(), Level.SEVERE);
        this.request = new Request("GET", "/someDir/", "HTTP/1.1", new ArrayList<RequestHeader>(), null, null);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCanHandleWhenDirectoryExists() throws Exception {
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.setDirectoryExists(true);
        this.directoryRequestHandler = new Directory(directoryToServe, fileInfo, logger);
        assertTrue("Can handle should return true when the directory exists.", this.directoryRequestHandler.canRespond(this.request));
    }

    @Test
    public void testCantHandleWhenDirectoryDoesNotExists() throws Exception {
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.setDirectoryExists(false);
        this.directoryRequestHandler = new Directory(directoryToServe, fileInfo, logger);
        assertFalse("Can handle should return false when the directory doesn't exist.", this.directoryRequestHandler.canRespond(this.request));
    }

    @Test
    public void testCantHandleIfIsNotAGetRequest() throws Exception {
        Request request = new Request("POST", "/someDir/", "HTTP/1.1", new ArrayList<RequestHeader>(), null, null);
        FileInfoMock fileInfo = new FileInfoMock();
        fileInfo.setDirectoryExists(true);
        this.directoryRequestHandler = new Directory(directoryToServe, fileInfo, logger);
        assertFalse("Can handle should return false when the directory doesn't exist.", this.directoryRequestHandler.canRespond(request));
    }

    private Logger logger;
}
