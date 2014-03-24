package uk.ac.ncl.cs.groupproject.Exception;

/**
 * @Auther: Li Zequn
 * Date: 20/03/14
 */
public class CommunicationFinishedException extends RuntimeException  {
    public CommunicationFinishedException(String message){
        super(message);
    }
    public CommunicationFinishedException(){
        super("Communication has already finished");
    }
}
