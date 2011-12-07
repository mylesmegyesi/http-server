package HttpServer.Utility;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author: Myles Megyesi
 */
public class InputStreamReader {

    public static final int carriageReturn = '\r';
    public static final int lineFeed = '\n';

    public String getNextLine(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        int nextByte;
        while ((nextByte = inputStream.read()) != -1 && !isReturn(nextByte, inputStream)) {
            builder.append((char) nextByte);
        }
        return builder.toString();
    }

    private boolean isReturn(int theByte, InputStream inputStream) throws IOException {
        return (theByte == carriageReturn && eatNextCharacterIfLineFeed(inputStream, theByte)) || theByte == lineFeed;
    }

    private boolean eatNextCharacterIfLineFeed(InputStream inputStream, int theByte) throws IOException {
        inputStream.mark(1);
        if (inputStream.read() != lineFeed) {
            inputStream.reset();
        }
        return true;
    }

    public String toString(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        byte[] buffer = new byte[4096];
        int numRead;
        while ((numRead = inputStream.read(buffer)) >= 0) {
            builder.append(new String(buffer, 0, numRead));
        }
        return builder.toString();
    }

}
