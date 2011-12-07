package HttpServer.Responses;

import HttpServer.Mocks.InputStreamMock;
import HttpServer.ResponseHeader;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Myles Megyesi
 */
public class OKTest {
    @Test
    public void hasCorrectParameters() throws Exception {
        OK ok = new OK(new ArrayList<ResponseHeader>(), new InputStreamMock(false));
        assertEquals("OK", ok.statusLine.reasonPhrase);
        assertEquals(200, ok.statusLine.statusCode);
    }
}
