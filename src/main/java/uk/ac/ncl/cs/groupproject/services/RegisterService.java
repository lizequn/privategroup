package uk.ac.ncl.cs.groupproject.services;

import org.springframework.stereotype.Service;
import uk.ac.ncl.cs.groupproject.entity.RegisterResponseEntity;

/**
 * Register Service
 * @Auther: Group1
 * Date: 06/03/14
 */

public interface RegisterService {
    /**
     * register user generate key pairs and store to database
     * @param id
     * @return the response information register user.
     */
    RegisterResponseEntity registerUser(String id);
}
