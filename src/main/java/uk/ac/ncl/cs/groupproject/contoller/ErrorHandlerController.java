package uk.ac.ncl.cs.groupproject.contoller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.ac.ncl.cs.groupproject.Exception.CommunicationAbortedException;
import uk.ac.ncl.cs.groupproject.Exception.CommunicationFinishedException;
import uk.ac.ncl.cs.groupproject.Exception.CommunicationNotFinishedException;

/**
 * @Auther: Li Zequn
 * Date: 07/03/14
 */
@ControllerAdvice
public class ErrorHandlerController {
    private static Logger log = Logger.getLogger(ErrorHandlerController.class);

    @ExceptionHandler(CommunicationFinishedException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String handleCFException(Exception e){
        // log.debug(e.getStackTrace());
        log.info(e.getMessage());
        // e.printStackTrace();
        return e.getMessage();
    }

    @ExceptionHandler(CommunicationAbortedException.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public String handleCAException(Exception e){
        // log.debug(e.getStackTrace());
        log.info(e.getMessage());
        // e.printStackTrace();
        return e.getMessage();
    }

    @ExceptionHandler(CommunicationNotFinishedException.class)
    @ResponseStatus(HttpStatus.NOT_MODIFIED)
    @ResponseBody
    public String handleCNException(CommunicationNotFinishedException e){
        // log.debug(e.getStackTrace());
        log.info(e.getMessage()+" " +e.getStage());

       // e.printStackTrace();
        return e.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ResponseBody
    public String handleException(Exception e){
       // log.debug(e.getStackTrace());
        log.info(e.getMessage());
        e.printStackTrace();
        return e.getMessage();
    }
}
