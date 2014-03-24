package uk.ac.ncl.cs.groupproject.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ncl.cs.groupproject.entity.GetMyExchangeResponseEntity;
import uk.ac.ncl.cs.groupproject.interceptor.Auth;
import uk.ac.ncl.cs.groupproject.services.GetMyExchangeService;

/**
 * @Auther: Li Zequn
 * Date: 25/02/14
 */
@Controller
public class GetMyExchangeController {

    @Autowired
    private GetMyExchangeService service;

    @Auth
    @ResponseBody
    @RequestMapping(value = "/getmyexchange/{name}",method = RequestMethod.POST)
    public GetMyExchangeResponseEntity getInfo(@PathVariable String name){
        return service.getInfoByName(name);
    }
}
