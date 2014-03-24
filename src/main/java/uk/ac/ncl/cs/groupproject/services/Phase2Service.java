package uk.ac.ncl.cs.groupproject.services;

import uk.ac.ncl.cs.groupproject.entity.Phase1RequestEntity;

import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 10/03/14
 */
public interface Phase2Service {
    Phase1RequestEntity getOriginSignatureEntity(String name,UUID uuid);
}
