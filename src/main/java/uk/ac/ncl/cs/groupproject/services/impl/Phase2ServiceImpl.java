package uk.ac.ncl.cs.groupproject.services.impl;

import org.springframework.stereotype.Service;

import uk.ac.ncl.cs.groupproject.communication.CommunicationManager;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeCommunication;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeStage;
import uk.ac.ncl.cs.groupproject.entity.Phase1RequestEntity;
import uk.ac.ncl.cs.groupproject.services.Phase2Service;

import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 10/03/14
 */
@Service
public class Phase2ServiceImpl implements Phase2Service {
    @Override
    public Phase1RequestEntity getOriginSignatureEntity(String name, UUID uuid) {
    	
    	if(null == uuid){
            throw new NullPointerException();
        }
        
    	if(null == name){
            throw new NullPointerException();
        }
        if("".equals(name)){
            throw new IllegalArgumentException("id could not be empty");
        }
    	
        FairExchangeCommunication process  =   CommunicationManager.getInstance().getProcess(uuid);
        process.setStage(FairExchangeStage.STAGE2);
        CommunicationManager.getInstance().update(uuid);
        return process.getEntity();
    }
}
