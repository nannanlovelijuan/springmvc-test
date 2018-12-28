package www.ezrpro.com.util;

import java.util.ArrayList;
import java.util.List;

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
    private MongoClient mongoClient;

    private List<ServerAddress> sas;
    MongoClientSingleton(){
        String mongoServer = ConfigHelp.getValue("mongodb.server");
        if (sas==null){
            sas = new ArrayList<>();
            for (String ip:mongoServer.split(";")) {
                sas.add(new ServerAddress(ip,20000));
            }
        }
        ClusterSettings clusterSettings = ClusterSettings
        .builder()
        .hosts(sas).build();
        MongoClientSettings settings = MongoClientSettings.builder().clusterSettings(clusterSettings).build();
        mongoClient = MongoClients.create(settings);
    }

    public MongoClient getMongoClient(){
        return mongoClient;
    }
    
}