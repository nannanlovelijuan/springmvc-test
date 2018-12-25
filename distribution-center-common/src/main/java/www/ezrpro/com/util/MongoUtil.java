package www.ezrpro.com.util;

import java.util.Arrays;

import com.mongodb.ServerAddress;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.connection.ClusterSettings;

import org.bson.Document;

/**
 * 
 * @auth: nanChen
 * @date: 2018-12-25 15:42:10
 * 
 */

public class MongoUtil{


    public static void insertOne(Document document,MongoCollection<Document> collection){
        collection.insertOne(document,new SingleResultCallback<Void>() {
            @Override
            public void onResult(final Void result, final Throwable t) {
                if(t == null){
                    System.out.println("Inserted!");
                }else{
                    System.out.println("存储失败");
                }

            }
        });
    }

}