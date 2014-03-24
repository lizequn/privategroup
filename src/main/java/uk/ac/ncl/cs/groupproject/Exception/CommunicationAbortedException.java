package uk.ac.ncl.cs.groupproject.Exception;

/**
 * @Auther: Li Zequn
 * Date: 18/03/14
 */
public class CommunicationAbortedException extends RuntimeException {
    public CommunicationAbortedException(String message){
        super(message);
    }
    public CommunicationAbortedException(){
        super("communication has been aborted");
    }
}
