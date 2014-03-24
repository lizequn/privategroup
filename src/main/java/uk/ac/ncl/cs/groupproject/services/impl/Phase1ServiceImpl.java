package uk.ac.ncl.cs.groupproject.services.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.ncl.cs.groupproject.communication.CommunicationManager;
import uk.ac.ncl.cs.groupproject.cyptoutil.Base64Coder;
import uk.ac.ncl.cs.groupproject.cyptoutil.HashUtil;
import uk.ac.ncl.cs.groupproject.cyptoutil.SignUtil;
import uk.ac.ncl.cs.groupproject.dao.FileDao;
import uk.ac.ncl.cs.groupproject.dao.FileEntity;
import uk.ac.ncl.cs.groupproject.dao.RegisterDao;
import uk.ac.ncl.cs.groupproject.entity.Phase1RequestEntity;
import uk.ac.ncl.cs.groupproject.services.Phase1Service;

import java.io.*;
import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 10/03/14
 */
@Service
public class Phase1ServiceImpl implements Phase1Service {
    private final static Logger log = Logger.getLogger(Phase1ServiceImpl.class);
    @Autowired
    private RegisterDao registerDao;
    @Autowired
    private FileDao dao;
    @Override
    public UUID checkSignature(Phase1RequestEntity entity,byte[] file) {
        byte[] publicKey =  registerDao.getPublicKeyById(entity.getFrom());
        if(publicKey == null){
            throw new IllegalArgumentException("the from address doesn't exist");
        }
        byte[] publicKey2 = registerDao.getPublicKeyById(entity.getTo());
        if(publicKey2 == null){
            throw new IllegalArgumentException("the to address doesn't exist");
        }

        String hash = HashUtil.calHash(file);

        String unSignedHash = new String(SignUtil.unSign(publicKey,Base64Coder.decode(entity.getSignedHash())));
        if(hash.equals(unSignedHash)){
            log.info("check origin signature success");
            UUID uuid = CommunicationManager.getInstance().addNewCommunication(entity.getFrom(),entity.getTo(),publicKey,publicKey2,entity.getSignedHash());
            CommunicationManager.getInstance().update(uuid);
            return uuid;
        }  else {
            throw new IllegalArgumentException("the signature is not fit for the file");
        }
    }

    @Override
    public void storeDoc(UUID uuid,String fileName ,InputStream file) throws IOException {
//        BufferedOutputStream stream =
//                new BufferedOutputStream(new FileOutputStream(new File(uuid + "-uploaded")));
//        stream.write(file);
//        stream.close();
    	
    	if(null == uuid){
            throw new NullPointerException();
        }
    	if(null == fileName){
            throw new NullPointerException();
        }
        if("".equals(fileName)){
            throw new IllegalArgumentException("id could not be empty");
        }
    	
    	
        FileEntity entity = new FileEntity();
        entity.setInputStream(file);
        entity.setFileName(fileName);
        dao.storeFile(uuid.toString(),entity);



    }
}
