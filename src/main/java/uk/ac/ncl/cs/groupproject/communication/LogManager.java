package uk.ac.ncl.cs.groupproject.communication;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uk.ac.ncl.cs.groupproject.dao.LogDao;
import uk.ac.ncl.cs.groupproject.dao.impl.LogDaoImpl;

import java.util.*;

/**
 * @Auther: Li Zequn
 * Date: 18/03/14
 */
@Component
public class LogManager {
    private static final Logger log = Logger.getLogger(LogManager.class);
    private static LogDao logDao = new LogDaoImpl();


    public static void recover(){
        CommunicationManager.getInstance().recover(logDao.getUnfinishedLog());
    }
    @Scheduled(fixedRate = 10000)
    public void cleanTask(){
        //log.info("clean communication check");
        List<FairExchangeCommunication> list =  CommunicationManager.getInstance().getFinishedCommunication();
        for(FairExchangeCommunication communication:list){
            log.info("do clean");
            logDao.storeLog(communication.getUuid(),communication);
        }
    }
    @Scheduled(fixedRate = 1000000)
    public void cleanTable(){
        logDao.deleteExpiredLog();
    }
}
