package uk.ac.ncl.cs.groupproject.services;

import uk.ac.ncl.cs.groupproject.dao.FileEntity;
import uk.ac.ncl.cs.groupproject.entity.Phase3RequestEntity;

import java.io.IOException;
import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 20/03/14
 */
public interface ResolveService {
    Phase3RequestEntity resolveSender(UUID uuid,String name);
    FileEntity resolveReceiver(UUID uuid,String name) throws IOException;
}
