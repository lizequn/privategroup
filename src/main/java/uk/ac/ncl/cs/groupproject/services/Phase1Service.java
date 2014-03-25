package uk.ac.ncl.cs.groupproject.services;

import uk.ac.ncl.cs.groupproject.entity.Phase1RequestEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 10/03/14
 */
public interface Phase1Service {
    UUID checkSignature(Phase1RequestEntity entity,byte[] file);
    void storeDoc(UUID uuid,String fileName,InputStream file) throws IOException;
    void changeDeliverMethod(UUID uuid);
}
