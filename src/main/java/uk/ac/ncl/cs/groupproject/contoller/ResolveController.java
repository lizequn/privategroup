package uk.ac.ncl.cs.groupproject.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ncl.cs.groupproject.dao.FileEntity;
import uk.ac.ncl.cs.groupproject.entity.Phase3RequestEntity;
import uk.ac.ncl.cs.groupproject.interceptor.Auth;
import uk.ac.ncl.cs.groupproject.services.AbortService;
import uk.ac.ncl.cs.groupproject.services.ResolveService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 20/03/14
 */
@Controller
public class ResolveController {
    @Autowired
    private ResolveService service;

    //@Auth
    @RequestMapping(value = "/resolve/sender/{uuid}")
    @ResponseBody
    public Phase3RequestEntity resolveSender(@PathVariable UUID uuid,@RequestHeader("name") String name) {
        return service.resolveSender(uuid,name);
    }

    @Auth
    @RequestMapping(value = "/resolve/receiver/{uuid}")
    public void resolveReceiver(@PathVariable UUID uuid,@RequestHeader("name") String name,HttpServletResponse response) throws IOException {
        FileEntity entity = service.resolveReceiver(uuid,name);

        response.setHeader("Content-Disposition","attachment; filename= "+entity.getFileName());
        response.setHeader("FileName",entity.getFileName());
        InputStream inputStream = entity.getInputStream();
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
