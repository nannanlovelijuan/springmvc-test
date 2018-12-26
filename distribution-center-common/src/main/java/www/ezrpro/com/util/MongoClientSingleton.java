package www.ezrpro.com.util;

import java.util.Arrays;

import com.mongodb.ServerAddress;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.async.client.MongoClients;
import com.mongodb.connection.ClusterSettings;

/**
* 单例模式获取MongoClient的连接
* @auth: nanChen
* @date: 2018-12-25  16:28:18
* 
*/

public enum MongoClientSingleton{

    INSTANCE;
    private MongoClient mongoClient =null;

    private MongoClientSingleton(){
        ClusterSettings clusterSettings = ClusterSettings
        .builder()
        .hosts(Arrays.asList(new ServerAddress("192.168.12.139",20000),new ServerAddress("192.168.12.141",20000),new ServerAddress("192.168.12.142",20000))).build();
        MongoClientSettings settings = MongoClientSettings.builder().clusterSettings(clusterSettings).build();
        mongoClient = MongoClients.create(settings);
    }

    public MongoClient getMongoClient(){
        return mongoClient;
    }
    
}