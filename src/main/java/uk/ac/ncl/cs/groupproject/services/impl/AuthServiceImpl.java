package uk.ac.ncl.cs.groupproject.services.impl;

import org.springframework.stereotype.Service;
import uk.ac.ncl.cs.groupproject.Exception.CommunicationAbortedException;
import uk.ac.ncl.cs.groupproject.Exception.CommunicationFinishedException;
import uk.ac.ncl.cs.groupproject.communication.CommunicationManager;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeCommunication;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeStage;
import uk.ac.ncl.cs.groupproject.services.AuthService;

import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 10/03/14
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public boolean checkAuthUUIDWithFromUser(UUID uuid, String fromUser,FairExchangeStage stage) {
        FairExchangeCommunication process = CommunicationManager.getInstance().getProcess(uuid);
        if(null == process) return false;
        if(process.getStage() == FairExchangeStage.STAGE5) throw new CommunicationFinishedException();
        if(process.getStage() == FairExchangeStage.STAGE6) throw new CommunicationAbortedException();
        if(!process.getFromAddress().equals(fromUser)) return false;
        if(process.getStage() != stage) return false;
        return true;
    }

    @Override
    public boolean checkAuthUUIDWithToUser(UUID uuid, String toUser) {
        FairExchangeCommunication process = CommunicationManager.getInstance().getProcess(uuid);
        if(null == process) return false;
        if(!process.getToAddress().equals(toUser)) return false;
        return true;
    }

    @Override
    public boolean checkAuthUUIDWithFromUser(UUID uuid, String fromUser) {
        FairExchangeCommunication process = CommunicationManager.getInstance().getProcess(uuid);
        if(null == process) return false;
        if(!process.getFromAddress().equals(fromUser)) return false;
        return true;
    }

    @Override
    public boolean checkAuthUUIDWithToUser(UUID uuid, String toUser,FairExchangeStage stage) {
        FairExchangeCommunication process = CommunicationManager.getInstance().getProcess(uuid);
        if(null == process) return false;
        if(process.getStage() == FairExchangeStage.STAGE5) throw new CommunicationFinishedException();
        if(process.getStage() == FairExchangeStage.STAGE6) throw new CommunicationAbortedException();
        if(!process.getToAddress().equals(toUser)) return false;
        if(process.getStage() != stage) return false;
        return true;
    }
}
