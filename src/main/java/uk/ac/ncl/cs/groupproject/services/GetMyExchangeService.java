package uk.ac.ncl.cs.groupproject.services;


import uk.ac.ncl.cs.groupproject.entity.GetMyExchangeResponseEntity;

public interface GetMyExchangeService {
    GetMyExchangeResponseEntity getInfoByName(String name);
}

