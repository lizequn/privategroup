package uk.ac.ncl.cs.groupproject.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.ncl.cs.groupproject.cyptoutil.Base64Coder;
import uk.ac.ncl.cs.groupproject.entity.Phase1RequestEntity;
import uk.ac.ncl.cs.groupproject.entity.Phase1ResponseEntity;
import uk.ac.ncl.cs.groupproject.interceptor.Auth;
import uk.ac.ncl.cs.groupproject.mail.MailUtil;
import uk.ac.ncl.cs.groupproject.mail.MyUrl;
import uk.ac.ncl.cs.groupproject.services.Phase1Service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 10/03/14
 */
@Controller
public class Phase1Controller {

    @Autowired
    private Phase1Service service;
    @Auth
    @RequestMapping(value="/phase1/normal", method= RequestMethod.POST)
    public @ResponseBody
    Phase1ResponseEntity handleFileUpload(@RequestParam("fromAddress") String id,
                                         @RequestParam("file") MultipartFile file,
                                         @RequestParam("toAddress") String toAddress,
                                         @RequestParam("signedHash") String codeBytes,
                                         @RequestHeader("name") String name
                                         ) throws IOException {
        if(!name.equals(id)){
            throw new IllegalArgumentException("from address not fit for the auth name");
        }
        if (!file.isEmpty()) {
            Phase1RequestEntity entity = new Phase1RequestEntity();
            entity.setFrom(id);
            entity.setTo(toAddress);
            entity.setSignedHash(codeBytes);
            String fileName = file.getOriginalFilename();
            UUID uid = service.checkSignature(entity,file.getBytes());
            service.storeDoc(uid,fileName,file.getInputStream());

            Phase1ResponseEntity entity1 = new Phase1ResponseEntity();
            entity1.setUuid(uid);
            return entity1;
        } else {
            throw new IllegalArgumentException("File is empty");
        }
    }
    @Auth
    @RequestMapping(value="/phase1/email", method= RequestMethod.POST)
    public @ResponseBody
    Phase1ResponseEntity handleFileUploadEmail(@RequestParam("fromAddress") String id,
                                          @RequestParam("file") MultipartFile file,
                                          @RequestParam("toAddress") String toAddress,
                                          @RequestParam("signedHash") String codeBytes,
                                          @RequestHeader("name") String name
    ) throws IOException {
        if(!name.equals(id)){
            throw new IllegalArgumentException("from address not fit for the auth name");
        }
        if (!file.isEmpty()) {
            Phase1RequestEntity entity = new Phase1RequestEntity();
            entity.setFrom(id);
            entity.setTo(toAddress);
            entity.setSignedHash(codeBytes);
            String fileName = file.getOriginalFilename();
            UUID uid = service.checkSignature(entity,file.getBytes());
            service.storeDoc(uid,fileName,file.getInputStream());
            Phase1ResponseEntity entity1 = new Phase1ResponseEntity();
            entity1.setUuid(uid);
            service.changeDeliverMethod(uid);
            MailUtil.sendMailtoReceiver(toAddress,name,fileName,uid.toString());
            return entity1;
        } else {
            throw new IllegalArgumentException("File is empty");
        }
    }
}
