package uk.ac.ncl.cs.groupproject.dao;

import uk.ac.ncl.cs.groupproject.communication.FairExchangeCommunication;

import java.util.Map;
import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 10/03/14
 */
public interface LogDao {
    void storeLog(UUID uuid,FairExchangeCommunication communication);
    FairExchangeCommunication getLog(UUID uuid);
    Map<UUID,FairExchangeCommunication> getUnfinishedLog();
    void deleteLog(UUID uuid);
    void deleteExpiredLog();
}
