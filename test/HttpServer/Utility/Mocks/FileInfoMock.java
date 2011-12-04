package HttpServer.Utility.Mocks;

import HttpServer.Utility.FileInfo;

/**
 * Author: Myles Megyesi
 */
public class FileInfoMock extends FileInfo {

    @Override
    public boolean fileExists(String directory, String file) {
        return this.isFileExists();
    }

    @Override
    public boolean directoryExists(String directoryServing, String directory) {
        return this.isDirectoryExists();
    }

    public boolean isFileExists() {
        return this.fileExists;
    }

    public void setFileExists(boolean fileExists) {
        this.fileExists = fileExists;
    }

    public boolean isDirectoryExists() {
        return directoryExists;
    }

    public void setDirectoryExists(boolean directoryExists) {
        this.directoryExists = directoryExists;
    }

    private boolean fileExists = false;
    private boolean directoryExists = false;
}
