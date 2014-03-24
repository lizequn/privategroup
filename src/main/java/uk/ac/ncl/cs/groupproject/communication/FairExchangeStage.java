package uk.ac.ncl.cs.groupproject.communication;

/**
 * @Auther: Li Zequn
 * Date: 25/02/14
 */
public enum FairExchangeStage {
    STAGE1("Phase 1",1),
    STAGE2("Signature is sent to Destination",2),
    STAGE3("Get Response from Bob",3),
    STAGE4("Send Doc to Bob,Wait Requester get Signature",4),
    STAGE5("Send Signature to Requester",5) ,
    STAGE6("aborted communication",9)
    ;

    private final String stageExplain;
    private final int index;
    FairExchangeStage(String stageExplain, int index){
        this.stageExplain = stageExplain;
        this.index = index;
    }

    public String getStageExplain() {
        return stageExplain;
    }

    public int getIndex() {
        return index;
    }
    public static FairExchangeStage fromNum(int index){
        switch (index){
            case 1:return STAGE1;
            case 2:return STAGE2;
            case 3:return STAGE3;
            case 4:return STAGE4;
            case 5:return STAGE5;
            case 9:return STAGE6;
            default:throw new IllegalArgumentException("the index must between 1 and 5");
        }
    }
}
