package uk.ac.ncl.cs.groupproject.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ncl.cs.groupproject.Exception.CommunicationNotFinishedException;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeStage;
import uk.ac.ncl.cs.groupproject.entity.Phase3RequestEntity;
import uk.ac.ncl.cs.groupproject.interceptor.Auth;
import uk.ac.ncl.cs.groupproject.services.AuthService;
import uk.ac.ncl.cs.groupproject.services.Phase5Service;

import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 11/03/14
 */
@Controller
public class Phase5Controller {

    @Autowired
    private AuthService authService;
    @Autowired
    private Phase5Service service ;

    @Auth
    @RequestMapping(value = "/phase5/{uuid}",method = RequestMethod.POST)
    @ResponseBody
    public Phase3RequestEntity getMyReceipt(@PathVariable UUID uuid, @RequestHeader("name") String name){
        if(!authService.checkAuthUUIDWithFromUser(uuid,name)){
            throw new IllegalArgumentException("uuid not fit for name");
        }
        if(!service.checkStage(uuid)){
            throw new CommunicationNotFinishedException();
        }
        return service.getSignature(uuid);
    }
}
