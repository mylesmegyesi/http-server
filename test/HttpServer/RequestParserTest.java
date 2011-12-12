package HttpServer;

import HttpServer.Exceptions.BadRequestException;
import HttpServer.Mocks.HttpRequestParserMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Myles Megyesi
 */
public class RequestParserTest {

    RequestParser requestParser;
    HttpRequestParserMock parser;

    @Before
    public void setUp() throws Exception {
        this.parser = new HttpRequestParserMock();
        this.requestParser = new RequestParser(this.parser);
    }

    @After
    public void tearDown() throws Exception {
        this.requestParser = null;
        this.parser = null;
    }

    @Test
    public void parseCallsParse() throws Exception {
        this.requestParser.parseRequestLineAndHeaders(null);
        assertEquals(1, this.parser.parseHeadersCalledCount);
    }

    @Test(expected = BadRequestException.class)
    public void throwsBadRequestOnParseException() throws Exception {
        this.parser.throwsOnParse = true;
        this.requestParser.parseRequestLineAndHeaders(null);
    }

    @Test
    public void parseBodyCallsParseBody() throws Exception {
        this.requestParser.parseBody(null, null, 0);
        assertEquals(1, this.parser.parseBodyCalledCount);
    }

    @Test(expected = BadRequestException.class)
    public void throwsBadRequestOnParseExceptionBody() throws Exception {
        this.parser.throwsOnParse = true;
        this.requestParser.parseBody(null, null, 0);
    }


}

