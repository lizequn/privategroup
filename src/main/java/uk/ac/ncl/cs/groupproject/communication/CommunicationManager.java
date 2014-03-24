package uk.ac.ncl.cs.groupproject.communication;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uk.ac.ncl.cs.groupproject.dao.LogDao;
import uk.ac.ncl.cs.groupproject.dao.impl.LogDaoImpl;

import java.util.*;

/**
 * @Auther: Li Zequn
 * Date: 10/03/14
 */
public class CommunicationManager {
    private static final Logger log = Logger.getLogger(CommunicationManager.class);
    private static final LogDao logDao  = new LogDaoImpl();
    //private static Map<UUID,FairExchangeCommunication> fairExchangeProcessMap = new HashMap<>();
    private static Map<UUID,FairExchangeCommunication> fairExchangeProcessMap = new LinkedHashMap<UUID,FairExchangeCommunication>(){
        @Override
        protected boolean removeEldestEntry(Map.Entry<UUID, FairExchangeCommunication> eldest) {
            if(this.size()>1000) {
                logDao.storeLog(eldest.getKey(),eldest.getValue());
                return true;
            }
            return false;
        }
    };
    private static CommunicationManager ourInstance = new CommunicationManager();

    public static CommunicationManager getInstance() {
        return ourInstance;
    }

    private CommunicationManager() {

    }
    public UUID addNewCommunication(String id,String toAddress,byte[] publicKeyFrom,byte[] publicKeyTo,String origin){
        FairExchangeCommunication fairExchangeCommunication = new FairExchangeCommunication(UUID.randomUUID(),id,toAddress,publicKeyFrom,publicKeyTo,origin);
        if(fairExchangeProcessMap.containsKey(fairExchangeCommunication.getUuid())){
            throw new IllegalArgumentException("this process already exist");
        }
        fairExchangeProcessMap.put(fairExchangeCommunication.getUuid(),fairExchangeCommunication);
        fairExchangeCommunication.setStage(FairExchangeStage.STAGE1);
        return fairExchangeCommunication.getUuid();
    }
    public FairExchangeCommunication getProcess(UUID id){
        FairExchangeCommunication communication = fairExchangeProcessMap.get(id);
        if(null == communication){
            communication = logDao.getLog(id);
        }
        return communication;
    }
    public List<FairExchangeCommunication> searchWaitingProcess(String name){
        return searchProcessByNameStage(name,FairExchangeStage.STAGE1);
    }

    public List<UUID> searchWaitingUUID(String name){
        return searchUUIDByNameStage(name,FairExchangeStage.STAGE1);
    }


    private List<FairExchangeCommunication> searchProcessByNameStage(String toName,FairExchangeStage stage){
        List<FairExchangeCommunication> list = new LinkedList<>();
        for(Map.Entry<UUID,FairExchangeCommunication> entry:logDao.getUnfinishedLog().entrySet()){
            if(entry.getValue().getToAddress().equals(toName) && entry.getValue().getStage() == stage){
                list.add(entry.getValue());
            }
        }
        return list;
    }
    private List<UUID> searchUUIDByNameStage(String toName,FairExchangeStage stage){
        List<UUID> list = new LinkedList<>();
        for(Map.Entry<UUID,FairExchangeCommunication> entry:logDao.getUnfinishedLog().entrySet()){
            if(entry.getValue().getToAddress().equals(toName) && entry.getValue().getStage() == stage){
                list.add(entry.getKey());
            }
        }
        return list;
    }
    public List<FairExchangeCommunication> getFinishedCommunication(){
        List<FairExchangeCommunication> list = new LinkedList<>();
        for(Map.Entry<UUID,FairExchangeCommunication> entry:fairExchangeProcessMap.entrySet()){
            if(entry.getValue().getStage() == FairExchangeStage.STAGE5){
                list.add(entry.getValue());
            }
        }
        for(FairExchangeCommunication communication:list){
            fairExchangeProcessMap.remove(communication.getUuid());
        }
        return list;
    }
    public void recover(Map<UUID,FairExchangeCommunication> map){
        log.info("map:"+map.size());
        fairExchangeProcessMap.putAll(map);
    }
    public void update(UUID uuid){
        logDao.storeLog(uuid, fairExchangeProcessMap.get(uuid));
    }
}
