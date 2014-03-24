package uk.ac.ncl.cs.groupproject.dao.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeCommunication;
import uk.ac.ncl.cs.groupproject.communication.FairExchangeStage;
import uk.ac.ncl.cs.groupproject.cyptoutil.Base64Coder;
import uk.ac.ncl.cs.groupproject.dao.DynamoDBConnectionPools;
import uk.ac.ncl.cs.groupproject.dao.FileDao;
import uk.ac.ncl.cs.groupproject.dao.LogDao;
import uk.ac.ncl.cs.groupproject.entity.RegisterResponseEntity;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Li Zequn
 * Date: 18/03/14
 */
@Repository
public class LogDaoImpl implements LogDao {
    private String tableName = "logInfo";
    private Logger log = Logger.getLogger(LogDaoImpl.class);
    private FileDao fileDao = new FileDaoImpl();

    @Override
    public void storeLog(UUID uuid, FairExchangeCommunication communication) {
        AmazonDynamoDBClient dbClient = DynamoDBConnectionPools.getInstance().getConnection();
        Map<String,AttributeValue> map = new HashMap<>();
        map.put("uuid",new AttributeValue(communication.getUuid().toString()));
        map.put("status",new AttributeValue().withN((communication.getStage() == FairExchangeStage.STAGE5? 0:1)+""));
        map.put("stage",new AttributeValue().withN(communication.getStage().getIndex() +""));
        map.put("fromAddress",new AttributeValue().withS(communication.getFromAddress()));
        map.put("toAddress",new AttributeValue().withS(communication.getToAddress()));
        map.put("fPublicKey",new AttributeValue().withB(ByteBuffer.wrap(communication.getfPublicKey())));
        map.put("tPublicKey",new AttributeValue().withB(ByteBuffer.wrap(communication.gettPublicKey())));
        map.put("originHash",new AttributeValue().withS(communication.getOriginHash()));
        map.put("time",new AttributeValue().withN(communication.getCreateTime()+""));
        if(communication.getStage().getIndex()>=4) {
            map.put("receiptHash",new AttributeValue().withS(communication.getReceiptHash()));
        }
        log.info("insert to log "+communication.getUuid().toString());
        PutItemRequest putItemRequest = new PutItemRequest(tableName,map);
        PutItemResult putItemResult = dbClient.putItem(putItemRequest);
        DynamoDBConnectionPools.getInstance().returnConnection(dbClient);
    }

    @Override
    public FairExchangeCommunication getLog(UUID uuid) {
        AmazonDynamoDBClient dbClient = DynamoDBConnectionPools.getInstance().getConnection();
        Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
        key.put("uuid", new AttributeValue().withS(uuid.toString()));

        GetItemRequest getItemRequest = new GetItemRequest()
                .withTableName(tableName)
                .withKey(key);

        GetItemResult result = dbClient.getItem(getItemRequest);

        return getObjectFromResult(result.getItem());
    }

    @Override
    public Map<UUID, FairExchangeCommunication> getUnfinishedLog() {
        long time = System.currentTimeMillis()-TimeUnit.MINUTES.toMillis(30);
        AmazonDynamoDBClient dbClient = DynamoDBConnectionPools.getInstance().getConnection();
        Map<UUID,FairExchangeCommunication> map = new HashMap<>();
        Map<String, AttributeValue> lastEvaluatedKey = null;
        do {

            Condition hashKeyCondition = new Condition()
                    .withComparisonOperator(ComparisonOperator.EQ.toString())
                    .withAttributeValueList(new AttributeValue().withN("1"));



            Map<String, Condition> keyConditions = new HashMap<String, Condition>();
            keyConditions.put("status", hashKeyCondition);
            ScanRequest scanRequest = new ScanRequest().withTableName(tableName)
                    .withScanFilter(keyConditions)
                    .withLimit(100).withExclusiveStartKey(lastEvaluatedKey);
            ScanResult result = dbClient.scan(scanRequest);
            log.info(result);
            for (Map<String, AttributeValue> item : result.getItems()) {
                FairExchangeCommunication communication = getObjectFromResult(item);
                log.info(TimeUnit.MINUTES.toMillis(30));
                log.info(communication.getCreateTime());
                log.info(time);
                log.info(System.currentTimeMillis());
                if(communication.getCreateTime()>time){
                    map.put(communication.getUuid(),communication);
                }
            }
            lastEvaluatedKey = result.getLastEvaluatedKey();
        } while (lastEvaluatedKey != null);
        log.info("recover num:"+map.size());
        DynamoDBConnectionPools.getInstance().returnConnection(dbClient);
        return map;
    }

    @Override
    public void deleteLog(UUID uuid) {
        AmazonDynamoDBClient dbClient = DynamoDBConnectionPools.getInstance().getConnection();
        Map<String,AttributeValue> map = new HashMap<>();
        map.put("uuid",new AttributeValue().withS(uuid.toString()));
        fileDao.deleteFile(uuid.toString());
        DeleteItemResult s =  dbClient.deleteItem(tableName,map);
        DynamoDBConnectionPools.getInstance().returnConnection(dbClient);

    }

    @Override
    public void deleteExpiredLog() {
        int i = 0;
        long expireTime = System.currentTimeMillis()-TimeUnit.DAYS.toMillis(1);
        AmazonDynamoDBClient dbClient = DynamoDBConnectionPools.getInstance().getConnection();
        Map<String, AttributeValue> lastEvaluatedKey = null;
        do {

            ScanRequest scanRequest = new ScanRequest().withTableName(tableName)
                    .withLimit(100).withExclusiveStartKey(lastEvaluatedKey);
            ScanResult result = dbClient.scan(scanRequest);
           // log.info(result);
            for (Map<String, AttributeValue> item : result.getItems()) {
                FairExchangeCommunication communication = getObjectFromResult(item);
                if(communication.getCreateTime() < expireTime){
                    i++;
                    deleteLog(communication.getUuid());
                }
            }
            lastEvaluatedKey = result.getLastEvaluatedKey();
        } while (lastEvaluatedKey != null);
        log.info("clear num:"+ i);

    }

    private FairExchangeCommunication getObjectFromResult(Map<String,AttributeValue> map){

        UUID uuid = UUID.fromString(map.get("uuid").getS());
        String fromAddress = map.get("fromAddress").getS();
        String toAddress = map.get("toAddress").getS();
        FairExchangeStage stage = FairExchangeStage.fromNum(Integer.parseInt(map.get("stage").getN()));
        byte[] fPublicKey = map.get("fPublicKey").getB().array();
        byte[] tPublicKey = map.get("tPublicKey").getB().array();
        String originHash = map.get("originHash").getS();
        long time = Long.valueOf(map.get("time").getN());
        FairExchangeCommunication communication = new FairExchangeCommunication(uuid,fromAddress,toAddress,fPublicKey,tPublicKey,originHash,time);
        communication.setStage(stage);
        if(communication.getStage().getIndex()>=4) {
            communication.setReceiptHash(map.get("receiptHash").getS());
        }
        return communication;
    }
    public static void main(String [] args){
        LogDaoImpl dao = new LogDaoImpl();
        int i = 0;
        AmazonDynamoDBClient dbClient = DynamoDBConnectionPools.getInstance().getConnection();
        Map<String, AttributeValue> lastEvaluatedKey = null;
        do {

            ScanRequest scanRequest = new ScanRequest().withTableName(dao.tableName)
                    .withLimit(100).withExclusiveStartKey(lastEvaluatedKey);
            ScanResult result = dbClient.scan(scanRequest);
            // log.info(result);
            for (Map<String, AttributeValue> item : result.getItems()) {
               // FairExchangeCommunication communication = dao.getObjectFromResult(item);
                    i++;
                   dao.deleteLog(UUID.fromString(item.get("uuid").getS()));
            }
            lastEvaluatedKey = result.getLastEvaluatedKey();
        } while (lastEvaluatedKey != null);
        dao.log.info("clear num:"+ i);

    }
}
