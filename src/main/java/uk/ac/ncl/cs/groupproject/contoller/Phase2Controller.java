package uk.ac.ncl.cs.groupproject.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeStage;
import uk.ac.ncl.cs.groupproject.entity.Phase1RequestEntity;
import uk.ac.ncl.cs.groupproject.entity.Phase1ResponseEntity;
import uk.ac.ncl.cs.groupproject.interceptor.Auth;
import uk.ac.ncl.cs.groupproject.services.AuthService;
import uk.ac.ncl.cs.groupproject.services.Phase2Service;

import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 10/03/14
 */
@Controller
public class Phase2Controller {
    @Autowired
    private AuthService authService;
    @Autowired
    private Phase2Service service;

    @Auth
    @RequestMapping(value = "/phase2/{id}/{uuid}", method = RequestMethod.POST)
    @ResponseBody
    public Phase1RequestEntity getSignOfOrigin(@PathVariable String id, @PathVariable UUID uuid,@RequestHeader("name") String name){
        if(!name.equals(id)){
            throw new IllegalArgumentException("from address not fit for the auth name");
        }
        if(!authService.checkAuthUUIDWithToUser(uuid,id, FairExchangeStage.STAGE1)){
            throw new IllegalArgumentException("this user "+name+"not fit for the uuid "+uuid);
        }
        return service.getOriginSignatureEntity(name,uuid);
    }
}
