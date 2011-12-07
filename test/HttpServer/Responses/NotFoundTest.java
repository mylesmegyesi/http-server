package HttpServer.Responses;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Myles Megyesi
 */
public class NotFoundTest {
    @Test
    public void hasCorrectParameters() throws Exception {
        NotFound notFound = new NotFound();
        assertEquals("Not Found", notFound.statusLine.reasonPhrase);
        assertEquals(404, notFound.statusLine.statusCode);
    }
}
