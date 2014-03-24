package uk.ac.ncl.cs.groupproject.contoller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ncl.cs.groupproject.entity.RegisterResponseEntity;
import uk.ac.ncl.cs.groupproject.services.RegisterService;

import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 06/03/14
 */
@Controller
public class RegisterController {

    private Logger log = Logger.getLogger(RegisterController.class);

    @Autowired
    private RegisterService service;

    @RequestMapping(value = "/register/{id}/{email}")
    @ResponseBody
    public RegisterResponseEntity register(@PathVariable String id,@PathVariable String email){
        log.info("register "+id);
        return service.registerUser(id);
    }
    private boolean verify(String email){
        String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(EMAIL_REGEX);
    }

}
