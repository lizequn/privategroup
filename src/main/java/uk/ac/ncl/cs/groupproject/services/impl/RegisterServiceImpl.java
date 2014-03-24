package uk.ac.ncl.cs.groupproject.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ncl.cs.groupproject.cyptoutil.Base64Coder;
import uk.ac.ncl.cs.groupproject.cyptoutil.KeyGenerator;
import uk.ac.ncl.cs.groupproject.dao.RegisterDao;
import uk.ac.ncl.cs.groupproject.entity.RegisterResponseEntity;
import uk.ac.ncl.cs.groupproject.services.RegisterService;

import java.security.KeyPair;

/**
 * @Auther: Li Zequn
 * Date: 07/03/14
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private RegisterDao dao;
    @Override
    public RegisterResponseEntity registerUser(String id) {
        if(null == id){
            throw new NullPointerException();
        }
        if("".equals(id)){
            throw new IllegalArgumentException("id could not be empty");
        }
        if(dao.checkAlreadyExist(id)){
            throw new IllegalArgumentException("the id has already registered");
        }
        KeyPair keyPair = KeyGenerator.generateNewKeyPairs();
        RegisterResponseEntity entity = new RegisterResponseEntity(id, Base64Coder.encode(keyPair.getPublic().getEncoded()),Base64Coder.encode(keyPair.getPrivate().getEncoded()));
        if(dao.registerUser(entity)){
            return entity;
        }
        return null;
    }
}
