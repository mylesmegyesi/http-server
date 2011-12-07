package HttpServer.Utility;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Author: Myles Megyesi
 */
public class InputStreamReaderTest extends TestCase {

    InputStream inputStream;
    InputStreamReader reader;

    public void setUp() throws Exception {
        reader = new InputStreamReader();
    }

    public void tearDown() throws Exception {
        reader = null;
        if (this.inputStream != null) {
            this.inputStream.close();
            this.inputStream = null;
        }
    }

    @Test
    public void testFirstLineWithLineFeed() throws Exception {
        this.inputStream = new ByteArrayInputStream("testline\na".getBytes());
        assertEquals("Not parsing line feeds correctly", "testline", reader.getNextLine(this.inputStream));
    }

    @Test
    public void testSecondLineWithLineFeed() throws Exception {
        this.inputStream = new ByteArrayInputStream("testline\na".getBytes());
        String firstLine = this.reader.getNextLine(this.inputStream);
        assertEquals("Not parsing line feeds correctly", "a", reader.getNextLine(this.inputStream));
    }

    @Test
    public void testFirstLineWithCarriageReturn() throws Exception {
        this.inputStream = new ByteArrayInputStream("testline\ra".getBytes());
        assertEquals("Not parsing line feeds correctly", "testline", reader.getNextLine(this.inputStream));
    }

    @Test
    public void testSecondLineWithCarriageReturn() throws Exception {
        this.inputStream = new ByteArrayInputStream("testline\ra".getBytes());
        String firstLine = this.reader.getNextLine(this.inputStream);
        assertEquals("Not parsing line feeds correctly", "a", reader.getNextLine(this.inputStream));
    }

    @Test
    public void testFirstLineWithCombination() throws Exception {
        this.inputStream = new ByteArrayInputStream("testline\r\na".getBytes());
        assertEquals("Not parsing line feeds correctly", "testline", reader.getNextLine(this.inputStream));
    }

    @Test
    public void testSecondLineWithCombination() throws Exception {
        this.inputStream = new ByteArrayInputStream("testline\r\na".getBytes());
        String firstLine = this.reader.getNextLine(this.inputStream);
        assertEquals("Not parsing line feeds correctly", "a", reader.getNextLine(this.inputStream));
    }

    @Test
    public void testWithMultipleLineFeeds() throws Exception {
        this.inputStream = new ByteArrayInputStream("testline\n\na".getBytes());
        String firstLine = this.reader.getNextLine(this.inputStream);
        String secondLine = this.reader.getNextLine(this.inputStream);
        assertEquals("Not parsing line feeds correctly", "a", reader.getNextLine(this.inputStream));
    }

    @Test
    public void testWithMultipleCarriageReturns() throws Exception {
        this.inputStream = new ByteArrayInputStream("testline\r\ra".getBytes());
        String firstLine = this.reader.getNextLine(this.inputStream);
        String secondLine = this.reader.getNextLine(this.inputStream);
        assertEquals("Not parsing line feeds correctly", "a", reader.getNextLine(this.inputStream));
    }

    @Test
    public void testWithMultipleCombinations() throws Exception {
        this.inputStream = new ByteArrayInputStream("testline\r\n\r\na".getBytes());
        String firstLine = this.reader.getNextLine(this.inputStream);
        String secondLine = this.reader.getNextLine(this.inputStream);
        assertEquals("Not parsing line feeds correctly", "a", reader.getNextLine(this.inputStream));
    }
}
