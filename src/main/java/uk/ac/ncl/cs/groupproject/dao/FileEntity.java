package uk.ac.ncl.cs.groupproject.dao;

import java.io.InputStream;

/**
 * @Auther: Li Zequn
 * Date: 11/03/14
 */
public class FileEntity {
    private String fileName;
    private InputStream inputStream;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
