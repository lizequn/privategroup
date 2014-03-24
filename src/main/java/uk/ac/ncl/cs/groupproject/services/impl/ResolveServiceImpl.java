package uk.ac.ncl.cs.groupproject.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ncl.cs.groupproject.Exception.CommunicationAbortedException;
import uk.ac.ncl.cs.groupproject.Exception.CommunicationNotFinishedException;
import uk.ac.ncl.cs.groupproject.communication.CommunicationManager;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeCommunication;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeStage;
import uk.ac.ncl.cs.groupproject.dao.FileDao;
import uk.ac.ncl.cs.groupproject.dao.FileEntity;
import uk.ac.ncl.cs.groupproject.dao.LogDao;
import uk.ac.ncl.cs.groupproject.entity.Phase3RequestEntity;
import uk.ac.ncl.cs.groupproject.services.AuthService;
import uk.ac.ncl.cs.groupproject.services.ResolveService;

import java.io.IOException;
import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 20/03/14
 */
@Service
public class ResolveServiceImpl implements ResolveService {
    @Autowired
    private LogDao dao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private AuthService service;
    @Override
    public FileEntity resolveReceiver(UUID uuid,String name) throws IOException {
        FairExchangeCommunication communication = CommunicationManager.getInstance().getProcess(uuid);
        if(null == communication){
            communication = dao.getLog(uuid);
            if(null == communication){
                throw new IllegalArgumentException("uuid does not exist");
            }
            if(!communication.getToAddress().equals(name)){
                throw new IllegalArgumentException("name is not fit for the uuid");
            }
            if(communication.getStage() == FairExchangeStage.STAGE5){
                return fileDao.getFile(uuid.toString());
            }else if(communication.getStage() == FairExchangeStage.STAGE6){
                throw new CommunicationAbortedException();
            }else {
                throw new CommunicationNotFinishedException(communication.getStage().getIndex());
            }
        } else {
            if(!communication.getToAddress().equals(name)){
                throw new IllegalArgumentException("name is not fit for the uuid");
            }
            if(communication.getStage() == FairExchangeStage.STAGE5){
                return fileDao.getFile(uuid.toString());
            }else if(communication.getStage() == FairExchangeStage.STAGE6){
                throw new CommunicationAbortedException();
            }else {
                throw new CommunicationNotFinishedException(communication.getStage().getIndex());
            }
        }
    }

    @Override
    public Phase3RequestEntity resolveSender(UUID uuid,String name) {
        FairExchangeCommunication communication = CommunicationManager.getInstance().getProcess(uuid);
        if(null == communication){
            communication = dao.getLog(uuid);
            if(null == communication){
                throw new IllegalArgumentException("uuid does not exist");
            }
            if(!communication.getFromAddress().equals(name)){
                throw new IllegalArgumentException("name is not fit for the uuid");
            }
            if(communication.getStage() == FairExchangeStage.STAGE4){
                communication.setStage(FairExchangeStage.STAGE5);
                return communication.getEntity2();
            } else if(communication.getStage() == FairExchangeStage.STAGE5){
                return communication.getEntity2();
            }else if(communication.getStage() == FairExchangeStage.STAGE6){
                throw new CommunicationAbortedException();
            }else {
                throw new CommunicationNotFinishedException(communication.getStage().getIndex());
            }

        } else {
            if(!communication.getFromAddress().equals(name)){
                throw new IllegalArgumentException("name is not fit for the uuid");
            }
            if(communication.getStage() == FairExchangeStage.STAGE4){
                communication.setStage(FairExchangeStage.STAGE5);
                return communication.getEntity2();
            } else if(communication.getStage() == FairExchangeStage.STAGE5){
                return communication.getEntity2();
            }else if(communication.getStage() == FairExchangeStage.STAGE6){
                throw new CommunicationAbortedException();
            }else {
                throw new CommunicationNotFinishedException(communication.getStage().getIndex());
            }
        }
    }
}
