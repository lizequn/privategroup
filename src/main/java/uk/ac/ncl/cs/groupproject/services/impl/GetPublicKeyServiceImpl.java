package uk.ac.ncl.cs.groupproject.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ncl.cs.groupproject.cyptoutil.Base64Coder;
import uk.ac.ncl.cs.groupproject.cyptoutil.KeyGenerator;
import uk.ac.ncl.cs.groupproject.dao.RegisterDao;
import uk.ac.ncl.cs.groupproject.entity.PublicKeyEntity;
import uk.ac.ncl.cs.groupproject.services.GetPublicKeyService;

/**
 * @Auther: Li Zequn
 * Date: 18/03/14
 */
@Service
public class GetPublicKeyServiceImpl implements GetPublicKeyService {
    @Autowired
    private RegisterDao registerDao;
    @Override
    public PublicKeyEntity getKey(String id) {
        if(null == id){
            throw new NullPointerException();
        }
        if("".equals(id)){
            throw new IllegalArgumentException("id could not be empty");
        }
        byte [] bytes = registerDao.getPublicKeyById(id);
        if(bytes == null){
            throw new IllegalArgumentException("the user doesn't exist");
        }
        PublicKeyEntity entity = new PublicKeyEntity();
        entity.setId(id);
        entity.setPublicKey(Base64Coder.encode(bytes));
        return entity;
    }
}
