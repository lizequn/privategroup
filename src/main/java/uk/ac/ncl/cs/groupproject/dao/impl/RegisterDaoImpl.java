package uk.ac.ncl.cs.groupproject.dao.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import uk.ac.ncl.cs.groupproject.cyptoutil.Base64Coder;
import uk.ac.ncl.cs.groupproject.dao.DynamoDBConnectionPools;
import uk.ac.ncl.cs.groupproject.dao.RegisterDao;
import uk.ac.ncl.cs.groupproject.entity.RegisterResponseEntity;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Li Zequn
 * Date: 07/03/14
 */
@Repository
public class RegisterDaoImpl implements RegisterDao {
    private String tableName = "registerUser";
    private Logger log = Logger.getLogger(RegisterDaoImpl.class);


    @Override
    public boolean checkAlreadyExist(String id) {
        assert(id != null);
        AmazonDynamoDBClient dbClient = DynamoDBConnectionPools.getInstance().getConnection();
        Map<String,AttributeValue> map = new HashMap<>();
        Condition condition = new Condition().withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(id));
        map.put("id",new AttributeValue().withS(id));
//        ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(map);
//        ScanResult scanResult = dbClient.scan(scanRequest);
        GetItemRequest getItemRequest = new GetItemRequest().withTableName(tableName).withKey(map);
        GetItemResult result = dbClient.getItem(getItemRequest);
        log.info("get result: " + result);
        DynamoDBConnectionPools.getInstance().returnConnection(dbClient);
        if(result.getItem() == null){
            return false;
        }
        return true;
    }


    @Override
    public boolean registerUser(RegisterResponseEntity entity) {
        assert (entity != null);
        AmazonDynamoDBClient dbClient = DynamoDBConnectionPools.getInstance().getConnection();
        Map<String,AttributeValue> map = new HashMap<>();
        map.put("id",new AttributeValue(entity.getId()));
        map.put("publicKey",new AttributeValue().withB(ByteBuffer.wrap(Base64Coder.decode(entity.getPublicKey()))));
        log.info("insert"+entity.getId());
        PutItemRequest putItemRequest = new PutItemRequest(tableName, map);
        PutItemResult putItemResult = dbClient.putItem(putItemRequest);
        DynamoDBConnectionPools.getInstance().returnConnection(dbClient);
        return true;
    }

    @Override
    public byte[] getPublicKeyById(String id) {
        assert(id != null);
        AmazonDynamoDBClient dbClient = DynamoDBConnectionPools.getInstance().getConnection();
        Map<String,AttributeValue> map = new HashMap<>();
        Condition condition = new Condition().withComparisonOperator(ComparisonOperator.EQ)
                .withAttributeValueList(new AttributeValue().withS(id));
        map.put("id",new AttributeValue().withS(id));
        GetItemRequest getItemRequest = new GetItemRequest().withTableName(tableName).withKey(map);
        GetItemResult result = dbClient.getItem(getItemRequest);
        log.info("get result: " + result);
        DynamoDBConnectionPools.getInstance().returnConnection(dbClient);
        if(result.getItem() == null){
           return null;
        }
        return result.getItem().get("publicKey").getB().array();
    }
}
