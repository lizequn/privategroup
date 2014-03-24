package uk.ac.ncl.cs.groupproject.services;

import uk.ac.ncl.cs.groupproject.entity.AbortEntity;

import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 19/03/14
 */
public interface AbortService {
    AbortEntity checkStageAndAbort(UUID uuid);
}
