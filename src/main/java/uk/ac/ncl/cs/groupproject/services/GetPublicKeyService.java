package uk.ac.ncl.cs.groupproject.services;

import uk.ac.ncl.cs.groupproject.entity.PublicKeyEntity;

/**
 * @Auther: Li Zequn
 * Date: 18/03/14
 */
public interface GetPublicKeyService {
    PublicKeyEntity getKey(String id);
}
