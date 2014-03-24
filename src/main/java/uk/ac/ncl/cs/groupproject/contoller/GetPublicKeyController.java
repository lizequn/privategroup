package uk.ac.ncl.cs.groupproject.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ncl.cs.groupproject.entity.PublicKeyEntity;
import uk.ac.ncl.cs.groupproject.services.GetPublicKeyService;

/**
 * @Auther: Li Zequn
 * Date: 18/03/14
 */
@Controller
public class GetPublicKeyController {
    @Autowired
    private GetPublicKeyService getPublicKeyService;
    @RequestMapping(value = "/getpublickey/{id}",method = RequestMethod.GET)
    @ResponseBody
    public PublicKeyEntity getPublicKey(@PathVariable String id){
        return getPublicKeyService.getKey(id);
    }
}
