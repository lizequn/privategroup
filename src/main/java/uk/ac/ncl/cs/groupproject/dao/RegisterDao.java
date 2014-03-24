package uk.ac.ncl.cs.groupproject.dao;

import org.springframework.stereotype.Repository;
import uk.ac.ncl.cs.groupproject.entity.RegisterResponseEntity;

/**
 * Dao 4 register
 * @Auther: Li Zequn
 * Date: 06/03/14
 */

public interface RegisterDao {
    /**
     * check already exist
     * @param id
     * @return boolean
     */
    boolean checkAlreadyExist(String id);
    /**
     * register user
     * @param entity
     * @return boolean
     */
    boolean registerUser(RegisterResponseEntity entity);

    /**
     *  get public key by given id
     * @param id
     * @return public key
     */
    byte [] getPublicKeyById(String id);
}
