package uk.ac.ncl.cs.groupproject.services.impl;

import org.springframework.stereotype.Service;
import uk.ac.ncl.cs.groupproject.communication.CommunicationManager;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeCommunication;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeStage;
import uk.ac.ncl.cs.groupproject.entity.AbortEntity;
import uk.ac.ncl.cs.groupproject.services.AbortService;

import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 19/03/14
 */
@Service
public class AbortServiceImpl implements AbortService {
    @Override
    public AbortEntity checkStageAndAbort(UUID uuid) {
    	
    	if(null == uuid){
            throw new NullPointerException();
        }
       
        FairExchangeCommunication communication =  CommunicationManager.getInstance().getProcess(uuid);
        if(communication.getStage().getIndex()>3){
            AbortEntity entity = new AbortEntity();
            entity.setSuccess(false);
            entity.setMessage("communication already in stage "+communication.getStage().getIndex());
            return entity;
        }
        AbortEntity entity = new AbortEntity();
        entity.setSuccess(true);
        entity.setMessage("");
        communication.setStage(FairExchangeStage.STAGE6);
        CommunicationManager.getInstance().getProcess(uuid);
        return entity;
    }
}
