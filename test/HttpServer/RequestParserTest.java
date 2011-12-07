package HttpServer;

import HttpServer.Exceptions.BadRequestException;
import HttpServer.Utility.InputStreamReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Author: Myles Megyesi
 */
public class RequestParserTest {

    RequestParser requestParser;
    InputStream inputStream;
    InputStreamReader inputStreamReader;

    @Before
    public void setUp() throws Exception {
        this.requestParser = new RequestParser();
        this.inputStreamReader = new InputStreamReader();
    }

    @After
    public void tearDown() throws Exception {
        this.requestParser = null;
        if (this.inputStream != null) {
            this.inputStream.close();
            this.inputStream = null;
        }
        this.inputStreamReader = null;
    }

    @Test
    public void splitsAStringWithOneSpace() throws Exception {
        String[] parts = this.requestParser.splitStringOnWhitespace("first second");
        assertEquals("Doesn't split into the right number of parts", 2, parts.length);
        assertEquals("Doesn't split the first word correctly", "first", parts[0]);
        assertEquals("Doesn't split the first word correctly", "second", parts[1]);
    }

    @Test
    public void splitsAStringWithOneTab() throws Exception {
        String[] parts = this.requestParser.splitStringOnWhitespace("first\tsecond");
        assertEquals("Doesn't split into the right number of parts", 2, parts.length);
        assertEquals("Doesn't split the first word correctly", "first", parts[0]);
        assertEquals("Doesn't split the first word correctly", "second", parts[1]);
    }

    @Test
    public void splitsAStringWithTwoSpaces() throws Exception {
        String[] parts = this.requestParser.splitStringOnWhitespace("first second");
        assertEquals("Doesn't split into the right number of parts", 2, parts.length);
        assertEquals("Doesn't split the first word correctly", "first", parts[0]);
        assertEquals("Doesn't split the first word correctly", "second", parts[1]);
    }

    @Test
    public void splitsAStringWithTwoTabs() throws Exception {
        String[] parts = this.requestParser.splitStringOnWhitespace("first\t\tsecond");
        assertEquals("Doesn't split into the right number of parts", 2, parts.length);
        assertEquals("Doesn't split the first word correctly", "first", parts[0]);
        assertEquals("Doesn't split the first word correctly", "second", parts[1]);
    }

    @Test
    public void splitsAStringWithALeadingSpace() throws Exception {
        String[] parts = this.requestParser.splitStringOnWhitespace(" first second");
        assertEquals("Doesn't split into the right number of parts", 2, parts.length);
        assertEquals("Doesn't split the first word correctly", "first", parts[0]);
        assertEquals("Doesn't split the first word correctly", "second", parts[1]);
    }

    @Test
    public void splitsAStringWithALeadingTab() throws Exception {
        String[] parts = this.requestParser.splitStringOnWhitespace("\tfirst second");
        assertEquals("Doesn't split into the right number of parts", 2, parts.length);
        assertEquals("Doesn't split the first word correctly", "first", parts[0]);
        assertEquals("Doesn't split the first word correctly", "second", parts[1]);
    }

    @Test
    public void splitsAStringWithATrailingSpace() throws Exception {
        String[] parts = this.requestParser.splitStringOnWhitespace("first second ");
        assertEquals("Doesn't split into the right number of parts", 2, parts.length);
        assertEquals("Doesn't split the first word correctly", "first", parts[0]);
        assertEquals("Doesn't split the first word correctly", "second", parts[1]);
    }

    @Test
    public void splitsAStringWithATrailingTab() throws Exception {
        String[] parts = this.requestParser.splitStringOnWhitespace("first second\t");
        assertEquals("Doesn't split into the right number of parts", 2, parts.length);
        assertEquals("Doesn't split the first word correctly", "first", parts[0]);
        assertEquals("Doesn't split the first word correctly", "second", parts[1]);
    }

    @Test
    public void parsesRequestUriWithoutQuery() throws Exception {
        String[] requestUriItems = this.requestParser.parseRequestUri("path");
        assertEquals("Not parsing request uri without a query correctly", "path", requestUriItems[0]);
        assertEquals("Not parsing request uri without a query correctly", "", requestUriItems[1]);
    }

    @Test
    public void parsesRequestUriWithQuestionMarkButNoQuery() throws Exception {
        String[] requestUriItems = this.requestParser.parseRequestUri("path?");
        assertEquals("Not parsing request uri with question mark but no query correctly", "path", requestUriItems[0]);
        assertEquals("Not parsing request uri with question mark but no query correctly", "", requestUriItems[1]);
    }

    @Test
    public void parsesRequestUriWithQuery() throws Exception {
        String[] requestUriItems = this.requestParser.parseRequestUri("path?query");
        assertEquals("Not parsing request uri with a query correctly", "path", requestUriItems[0]);
        assertEquals("Not parsing request uri with a query correctly", "query", requestUriItems[1]);
    }

    @Test
    public void parsesJustQuestionMarkString() throws Exception {
        String[] requestUriItems = this.requestParser.parseRequestUri("?");
        assertEquals("Not parsing just question mark correctly", "", requestUriItems[0]);
        assertEquals("Not parsing just question mart correctly", "", requestUriItems[1]);
    }

    @Test
    public void parsesEmptyString() throws Exception {
        String[] requestUriItems = this.requestParser.parseRequestUri("");
        assertEquals("Not parsing empty string", "", requestUriItems[0]);
        assertEquals("Not parsing empty string", "", requestUriItems[1]);
    }

    @Test
    public void assignsThreeRequestItems() throws Exception {
        String[] requestLineItems = new String[]{"action", "uri", "protocol"};
        RequestLine requestLine = this.requestParser.parseRequestItems(requestLineItems);
        assertEquals("Not assigning request line items correctly", requestLineItems[0], requestLine.action);
        assertEquals("Not assigning request line items correctly", requestLineItems[1], requestLine.requestUri);
        assertEquals("Not assigning request line items correctly", requestLineItems[2], requestLine.protocolVersion);
    }

    @Test(expected = BadRequestException.class)
    public void throwsAnExceptionIfLessThanThreeItems() throws Exception {
        this.inputStream = new ByteArrayInputStream("GET SOMETHING".getBytes());
        this.requestParser.parseRequestLine(this.inputStream, this.inputStreamReader);
    }

    @Test(expected = BadRequestException.class)
    public void throwsAnExceptionIfMoreThanThreeItems() throws Exception {
        this.inputStream = new ByteArrayInputStream("GET SOMETHING HTTP ELSE".getBytes());
        this.requestParser.parseRequestLine(this.inputStream, this.inputStreamReader);
    }

    @Test
    public void doesNotThrowAnExceptionForThreeItems() throws Exception {
        this.inputStream = new ByteArrayInputStream("GET SOMETHING HTTP".getBytes());
        try {
            this.requestParser.parseRequestLine(this.inputStream, this.inputStreamReader);
        } catch (Exception e) {
            fail("Was not supposed to throw an exception");
        }
    }

    @Test
    public void parsesAHeader() throws Exception {
        RequestHeader requestHeader = this.requestParser.parseHeader("Some: header");
        assertEquals("Request header parser parses the header name.", "Some", requestHeader.name);
        assertEquals("Request header parser parses the header name.", "header", requestHeader.value);
    }

    @Test(expected = BadRequestException.class)
    public void throwsAnExceptionIfTheColonIsAtTheBeginningOfTheHeader() throws Exception {
        this.requestParser.parseHeader(": header");
    }

    @Test(expected = BadRequestException.class)
    public void throwsAnExceptionIfTheColonIsAtTheEndOfTheHeader() throws Exception {
        this.requestParser.parseHeader("header:");
    }

    @Test(expected = BadRequestException.class)
    public void throwsAnExceptionIfTheColonDoesntExist() throws Exception {
        this.requestParser.parseHeader("header");
    }

    @Test
    public void parsesTwoHeaders() throws Exception {
        this.inputStream = new ByteArrayInputStream("Some: header\nAnother:header2\n\n".getBytes());
        List<RequestHeader> requestHeaders = this.requestParser.parseHeaders(this.inputStream, this.inputStreamReader);
        assertEquals("Request header parser returns the wrong number of headers.", 2, requestHeaders.size());
        assertEquals("Request header parser parses the header name.", "Some", requestHeaders.get(0).name);
        assertEquals("Request header parser parses the header name.", "header", requestHeaders.get(0).value);
        assertEquals("Request header parser parses the header name.", "Another", requestHeaders.get(1).name);
        assertEquals("Request header parser parses the header name.", "header2", requestHeaders.get(1).value);
    }

    @Test
    public void stopsParsingAtAnEmptyLine() throws Exception {
        this.inputStream = new ByteArrayInputStream("Some: header\n\nAnother:header2\n\n".getBytes());
        List<RequestHeader> requestHeaders = this.requestParser.parseHeaders(this.inputStream, this.inputStreamReader);
        assertEquals("Request header parser returns the wrong number of headers.", 1, requestHeaders.size());
        assertEquals("Request header parser parses the header name.", "Some", requestHeaders.get(0).name);
        assertEquals("Request header parser parses the header name.", "header", requestHeaders.get(0).value);
    }

    @Test
    public void assignsTheBodyToTheLeftOverStream() throws Exception {
        this.inputStream = new ByteArrayInputStream("GET /path/script.cgi?field1=value HTTP/1.0\n\nhome=Cosby&favorite+flavor=flies".getBytes());
        Request request = this.requestParser.parse(this.inputStream, this.inputStreamReader);
        assertEquals("Body isn't parsed correctly.", "home=Cosby&favorite+flavor=flies", inputStreamReader.toString(request.body));
    }
}

