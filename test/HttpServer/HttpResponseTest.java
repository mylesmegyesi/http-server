package HttpServer;

import HttpServer.Mocks.ResponseMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Author: Myles Megyesi
 */
public class HttpResponseTest {

    Response response;

    @Before
    public void setUp() throws Exception {
        response = new ResponseMock();
    }

    @After
    public void tearDown() throws Exception {
        response = null;
    }

    @Test
    public void buildsStatusLine() throws Exception {
        ResponseMock response = new ResponseMock();
        String expectedStatusLine = new Scanner(response.getMockResponseString()).nextLine();
        String statusLine = new Scanner(response.getResponseString()).nextLine();
        assertEquals("Builds status line incorrectly.", expectedStatusLine, statusLine);
    }

    @Test
    public void buildsResponseCorrectly() throws Exception {
        ResponseMock response = new ResponseMock();
        assertTrue("Builds response incorrectly.", responsesAreEqual(response.getMockResponseString(), response.getResponseString()));
    }

    private boolean responsesAreEqual(String response1, String response2) {
        String responseNoDate1 = this.removeDateFromString(response1);
        String responseNoDate2 = this.removeDateFromString(response2);
        return responseNoDate1.equals(responseNoDate2);
    }

    private String removeDateFromString(String response) {
        Scanner scanner = new Scanner(response);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.contains("Date: ")) {
                builder.append(line);
                builder.append("\r\n");
            }
        }
        return builder.toString();
    }
}
