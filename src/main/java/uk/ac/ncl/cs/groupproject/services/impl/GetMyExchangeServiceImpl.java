package uk.ac.ncl.cs.groupproject.services.impl;

import org.springframework.stereotype.Service;

import uk.ac.ncl.cs.groupproject.communication.CommunicationManager;
import uk.ac.ncl.cs.groupproject.entity.GetMyExchangeResponseEntity;
import uk.ac.ncl.cs.groupproject.services.GetMyExchangeService;

/**
 * @Auther: Li Zequn
 * Date: 28/02/14
 */
@Service
public class GetMyExchangeServiceImpl implements GetMyExchangeService {

    @Override
    public GetMyExchangeResponseEntity getInfoByName(String name) {
    	
    	if(null == name){
            throw new NullPointerException();
        }
        if("".equals(name)){
            throw new IllegalArgumentException("id could not be empty");
        }
    	
        GetMyExchangeResponseEntity exchangeResponseEntity = new GetMyExchangeResponseEntity();
        exchangeResponseEntity.setLists(CommunicationManager.getInstance().searchWaitingUUID(name));
        return exchangeResponseEntity;
    }
}
