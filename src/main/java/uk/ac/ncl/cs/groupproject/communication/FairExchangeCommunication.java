package uk.ac.ncl.cs.groupproject.communication;

import uk.ac.ncl.cs.groupproject.entity.Phase1RequestEntity;
import uk.ac.ncl.cs.groupproject.entity.Phase3RequestEntity;

import java.util.UUID;

/**
 * @Auther: Li Zequn
 * Date: 10/03/14
 */
public class FairExchangeCommunication {
    private final UUID uuid;
    private final String toAddress;
    private final String fromAddress;
    private final byte[] fPublicKey;
    private final byte[] tPublicKey;
    private final String originHash;
    private String receiptHash;
    private final long createTime;


    private Phase1RequestEntity entity;
    private Phase3RequestEntity entity2;
    private FairExchangeStage stage;

    public FairExchangeCommunication( UUID uuid,String fromAddress, String toAddress,byte[]  fPublicKey, byte[]  tPublicKey, String originHash) {
        this.fPublicKey = fPublicKey;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.tPublicKey = tPublicKey;
        this.uuid = uuid;
        this.originHash = originHash;
        this.createTime = System.currentTimeMillis();
        stage = FairExchangeStage.STAGE1;
    }
    public FairExchangeCommunication( UUID uuid,String fromAddress, String toAddress,byte[]  fPublicKey, byte[]  tPublicKey, String originHash,long time) {
        this.fPublicKey = fPublicKey;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.tPublicKey = tPublicKey;
        this.uuid = uuid;
        this.originHash = originHash;
        this.createTime = time;
        stage = FairExchangeStage.STAGE1;
    }

    public long getCreateTime() {
        return createTime;
    }

    public Phase3RequestEntity getEntity2() {
        if(entity2 == null){
            entity2 = new Phase3RequestEntity();
            entity2.setReceiptHash(receiptHash);
        }
        return entity2;
    }

    public void setEntity2(Phase3RequestEntity entity2) {
        this.entity2 = entity2;
    }

    public Phase1RequestEntity getEntity() {
        if(entity == null){
            entity = new Phase1RequestEntity();
            entity.setFrom(fromAddress);
            entity.setTo(toAddress);
            entity.setSignedHash(originHash);
        }
        return entity;
    }

    public void setEntity(Phase1RequestEntity entity) {
        this.entity = entity;
    }

    public FairExchangeStage getStage() {
        return stage;
    }

    public void setStage(FairExchangeStage stage) {
        this.stage = stage;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public byte[] getfPublicKey() {
        return fPublicKey;
    }

    public byte[] gettPublicKey() {
        return tPublicKey;
    }

    public String getOriginHash() {
        return originHash;
    }

    public String getReceiptHash() {
        return receiptHash;
    }

    public void setReceiptHash(String receiptHash) {
        this.receiptHash = receiptHash;
    }
}
