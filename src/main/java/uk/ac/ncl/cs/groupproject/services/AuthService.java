package uk.ac.ncl.cs.groupproject.services;

import uk.ac.ncl.cs.groupproject.communication.FairExchangeStage;

import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 10/03/14
 */
public interface AuthService {
    boolean checkAuthUUIDWithToUser(UUID uuid,String toUser,FairExchangeStage stage);
    boolean checkAuthUUIDWithFromUser(UUID uuid,String fromUser,FairExchangeStage stage);
    boolean checkAuthUUIDWithToUser(UUID uuid,String toUser);
    boolean checkAuthUUIDWithFromUser(UUID uuid,String fromUser);
}
