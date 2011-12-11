package HttpServer;

import HttpServer.Mocks.SocketMock;
import HttpServer.Utility.Logging;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Myles Megyesi
 */
public class RequestDispatcherFactoryTest {
    @Test
    public void createsARequestDispatcher() throws Exception {
        Logger logger = new Logging().getLoggerAndSetLevel(this.getClass().getName(), Level.OFF);
        RequestDispatcherFactory requestDispatcherFactory = new RequestDispatcherFactory(new RequestParser(), new ResponseWriter(), new ArrayList<RequestHandler>(), logger);
        assertEquals(RequestDispatcher.class, requestDispatcherFactory.create(new SocketMock(false, false)).getClass());
    }
}
