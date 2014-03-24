package uk.ac.ncl.cs.groupproject.Exception;

/**
 * @Auther: Li Zequn
 * Date: 18/03/14
 */
public class CommunicationNotFinishedException extends RuntimeException {
    private int stage;
    public CommunicationNotFinishedException(String message,int stage){
        super(message);
        this.stage = stage;
    }
    public CommunicationNotFinishedException(int stage){
        super("communication not finished");
        this.stage = stage;
    }
    public CommunicationNotFinishedException(){
        super("communication not finished");
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
}
