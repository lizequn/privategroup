package uk.ac.ncl.cs.groupproject.dao;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Auther: Li Zequn
 * Date: 10/03/14
 */
public interface FileDao {
    void storeFile(String key,FileEntity fileEntity);
    FileEntity getFile(String key) throws IOException;
    InputStream test();
    void deleteFile(String key);
}
