package uk.ac.ncl.cs.groupproject.interceptor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import uk.ac.ncl.cs.groupproject.cyptoutil.Base64Coder;
import uk.ac.ncl.cs.groupproject.cyptoutil.SignUtil;
import uk.ac.ncl.cs.groupproject.dao.RegisterDao;
import uk.ac.ncl.cs.groupproject.services.RegisterService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ZequnLi
 *         Date: 14-2-24
 */
public class HeaderInterceptor extends HandlerInterceptorAdapter {
    private static Logger log = Logger.getLogger(HeaderInterceptor.class);
    @Autowired
    private RegisterDao dao;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
        if(null == auth){
            return true;
        }
        String name = request.getHeader("name");
        String authString = request.getHeader("auth_token");
        boolean result = checkHeaderAuth(name, Base64Coder.decode(authString));
        if(!result){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        }
        log.info("Auth_Header:" +name+" "+result);
        return result;
    }
    private boolean checkHeaderAuth(String name,byte[] sign){
        byte[] publicKey = dao.getPublicKeyById(name);
        if(publicKey == null){
            return false;
        }

        byte[] s = SignUtil.unSign(publicKey, sign);

        if(name.equals(new String(s))){
            return true;
        }
        return false;
    }
}
