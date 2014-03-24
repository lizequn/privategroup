package uk.ac.ncl.cs.groupproject.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ncl.cs.groupproject.communication.CommunicationManager;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeCommunication;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeStage;
import uk.ac.ncl.cs.groupproject.entity.AbortEntity;
import uk.ac.ncl.cs.groupproject.interceptor.Auth;
import uk.ac.ncl.cs.groupproject.services.AbortService;
import uk.ac.ncl.cs.groupproject.services.AuthService;

import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 19/03/14
 */
@Controller
public class AbortController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AbortService service;
    @Auth
    @RequestMapping(value = "/abort/{id}/{uuid}", method = RequestMethod.POST)
    @ResponseBody
    public AbortEntity abort(@PathVariable String id, @PathVariable UUID uuid){


        //int stage = fairExchangeStage.getIndex();
        if(!authService.checkAuthUUIDWithToUser(uuid,id)){
            throw new IllegalArgumentException("this user cannot abort");
        }

        return service.checkStageAndAbort(uuid);
    }

}
