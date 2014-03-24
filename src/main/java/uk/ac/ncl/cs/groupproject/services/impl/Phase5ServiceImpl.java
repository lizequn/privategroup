package uk.ac.ncl.cs.groupproject.services.impl;

import org.springframework.stereotype.Service;

import uk.ac.ncl.cs.groupproject.communication.CommunicationManager;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeCommunication;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeStage;
import uk.ac.ncl.cs.groupproject.entity.Phase3RequestEntity;
import uk.ac.ncl.cs.groupproject.services.Phase5Service;

import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 11/03/14
 */
@Service
public class Phase5ServiceImpl implements Phase5Service {
    @Override
    public boolean checkStage(UUID uuid) {
    	
    	if(null == uuid){
            throw new NullPointerException();
        }
    	
        CommunicationManager manager = CommunicationManager.getInstance();
        FairExchangeCommunication communication = manager.getProcess(uuid);
        assert (communication != null);
        if(communication.getStage() == FairExchangeStage.STAGE4){
            return true;
        }
        return false;
    }

    @Override
    public Phase3RequestEntity getSignature(UUID uuid) {
    	
    	if(null == uuid){
            throw new NullPointerException();
        }
        
    	
        CommunicationManager manager = CommunicationManager.getInstance();
        FairExchangeCommunication communication = manager.getProcess(uuid);
        assert (communication != null);
        communication.setStage(FairExchangeStage.STAGE5);
        CommunicationManager.getInstance().update(uuid);
        return communication.getEntity2();
    }
}
