package uk.ac.ncl.cs.groupproject.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.ncl.cs.groupproject.communication.CommunicationManager;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeCommunication;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeStage;
import uk.ac.ncl.cs.groupproject.cyptoutil.Base64Coder;
import uk.ac.ncl.cs.groupproject.cyptoutil.HashUtil;
import uk.ac.ncl.cs.groupproject.cyptoutil.SignUtil;
import uk.ac.ncl.cs.groupproject.dao.FileDao;
import uk.ac.ncl.cs.groupproject.dao.FileEntity;
import uk.ac.ncl.cs.groupproject.entity.Phase3RequestEntity;
import uk.ac.ncl.cs.groupproject.mail.MailUtil;
import uk.ac.ncl.cs.groupproject.services.Phase3Service;

import java.io.IOException;
import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 11/03/14
 */
@Service
public class Phase3ServiceImpl implements Phase3Service {
    @Autowired
    private FileDao dao;
    @Override
    public FileEntity checkSignatureGetFile(UUID uuid ,Phase3RequestEntity entity) throws IOException {
    	
    	if(null == uuid){
            throw new NullPointerException();
        }
    	if(null == entity){
            throw new NullPointerException();
        }
    	
    	FairExchangeCommunication communication = CommunicationManager.getInstance().getProcess(uuid);

        byte[] unSigB = SignUtil.unSign(communication.gettPublicKey(), Base64Coder.decode(entity.getReceiptHash()));
        byte[] sigA = Base64Coder.decode(communication.getOriginHash());
        String hashedSigA = HashUtil.calHash(sigA);

        if(!hashedSigA.equals(new String(unSigB))){
            throw new IllegalArgumentException("signature is not right");
        }
        communication.setStage(FairExchangeStage.STAGE4);
        communication.setReceiptHash(entity.getReceiptHash());
        CommunicationManager.getInstance().update(uuid);
        if(communication.getDeliver() == 1){
            MailUtil.sendMailToSender(communication.getFromAddress(),communication.getUuid().toString(),communication.getUuid().toString());
        }
        return dao.getFile(uuid.toString());
    }


}
