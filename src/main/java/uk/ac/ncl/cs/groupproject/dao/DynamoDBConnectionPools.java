package uk.ac.ncl.cs.groupproject.dao;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Li Zequn
 * Date: 07/03/14
 */
public class DynamoDBConnectionPools {
    private static DynamoDBConnectionPools ourInstance = new DynamoDBConnectionPools();
    public static DynamoDBConnectionPools getInstance() {
        return ourInstance;
    }

    private static int maxNum = 100;
    private static int minNum = 1;
    private static int counter = 0;
    private LinkedList<AmazonDynamoDBClient> pool;
    private ArrayList<AmazonDynamoDBClient> working;
    private DynamoDBConnectionPools() {
        pool = new LinkedList<>();
        working = new ArrayList<>();
        for(int i =0;i<minNum;i++){
            AmazonDynamoDBClient client = createConnection();
            pool.add(client);
        }
    }

    public AmazonDynamoDBClient getConnection(){
        if(pool.size()>0){
            AmazonDynamoDBClient client = pool.getFirst();
            working.add(client);
            return client;
        } else {
            AmazonDynamoDBClient client = createConnection();
            working.add(client);
            return client;
        }
    }

    public void returnConnection(AmazonDynamoDBClient client){
        if(working.contains(client)){
            working.remove(working.indexOf(client));
            if(counter<maxNum){
                pool.add(client);
            }else {
                closeConnection(client);
            }
        }
    }

    private void closeConnection(AmazonDynamoDBClient client){
        client.shutdown();
        counter--;
    }

    private AmazonDynamoDBClient createConnection(){
        counter++;
        AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ClasspathPropertiesFileCredentialsProvider());
        Region region = Region.getRegion(Regions.EU_WEST_1);
        client.setRegion(region);
        return client;
    }
}
