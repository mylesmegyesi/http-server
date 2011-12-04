package HttpServer.Utility;

import java.io.File;
import java.io.IOException;

/**
 * Author: Myles Megyesi
 */
public class FileInfo {

    public boolean fileExists(String directory, String file) {
        File fileToServe = new File(directory, file);
        return fileToServe.exists() && fileToServe.isFile();
    }

    public boolean directoryExists(String directoryServing, String directory) {
        File directoryToServe = new File(directoryServing, directory);
        return directoryToServe.exists() && directoryToServe.isDirectory();
    }

    public String getRelativePath(String parentDir, String entry) {
        return new File(parentDir, entry).getPath();
    }

    public String[] getEntries(String parentDirectory, String directory) {
        return new File(parentDirectory, directory).list();
    }

}
