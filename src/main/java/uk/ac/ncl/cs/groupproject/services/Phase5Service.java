package uk.ac.ncl.cs.groupproject.services;

import uk.ac.ncl.cs.groupproject.entity.Phase3RequestEntity;

import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 11/03/14
 */
public interface Phase5Service {
    boolean checkStage(UUID uuid);
    Phase3RequestEntity getSignature(UUID uuid);
}
