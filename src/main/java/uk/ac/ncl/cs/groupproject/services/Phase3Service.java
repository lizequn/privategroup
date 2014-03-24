package uk.ac.ncl.cs.groupproject.services;

import uk.ac.ncl.cs.groupproject.dao.FileEntity;
import uk.ac.ncl.cs.groupproject.entity.Phase3RequestEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 11/03/14
 */
public interface Phase3Service {
    FileEntity checkSignatureGetFile(UUID uuid,Phase3RequestEntity entity) throws IOException;

}
