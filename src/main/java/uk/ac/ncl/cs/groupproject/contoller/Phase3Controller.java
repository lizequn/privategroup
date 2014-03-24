package uk.ac.ncl.cs.groupproject.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeStage;
import uk.ac.ncl.cs.groupproject.dao.FileDao;
import uk.ac.ncl.cs.groupproject.dao.FileEntity;
import uk.ac.ncl.cs.groupproject.entity.Phase3RequestEntity;
import uk.ac.ncl.cs.groupproject.interceptor.Auth;
import uk.ac.ncl.cs.groupproject.services.AuthService;
import uk.ac.ncl.cs.groupproject.services.Phase3Service;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 10/03/14
 */
@Controller
public class Phase3Controller {
    @Autowired
    private AuthService authService;

    @Autowired
    private Phase3Service service;

    @Auth
    @RequestMapping(value = "/phase3/{id}/{uuid}",method = RequestMethod.POST)
    public void phase3GetFile(@PathVariable String id,@PathVariable UUID uuid,@RequestHeader("name") String name,@RequestBody Phase3RequestEntity entity,HttpServletResponse response) throws IOException {
        if(!name.equals(id)){
            throw new IllegalArgumentException("from address not fit for the auth name");
        }
        if(!authService.checkAuthUUIDWithToUser(uuid,id, FairExchangeStage.STAGE2)){
            throw new IllegalArgumentException("this user "+name+"not fit for the uuid "+uuid);
        }
        FileEntity entity1 = service.checkSignatureGetFile(uuid,entity);
        response.setHeader("Content-Disposition","attachment; filename= "+entity1.getFileName());
        response.setHeader("FileName",entity1.getFileName());
        InputStream inputStream = entity1.getInputStream();
        int read=0;
        byte[] bytes = new byte[1000];
        OutputStream os = response.getOutputStream();

        while((read = inputStream.read(bytes))!= -1){
            os.write(bytes, 0, read);
        }
        os.flush();
        os.close();
    }

}
