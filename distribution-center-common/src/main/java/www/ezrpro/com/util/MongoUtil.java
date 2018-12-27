package www.ezrpro.com.util;

import java.util.List;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoCollection;

import org.bson.Document;

/**
 * 
 * @auth: nanChen
 * @date: 2018-12-25 15:42:10
 * 
 */

public class MongoUtil{

    private static SingleResultCallback<Void> src = new SingleResultCallback<Void>() {
        @Override
        public void onResult(final Void result, final Throwable t) {
            if(t == null){
                System.out.println("Inserted!");
            }else{
                System.out.println("存储失败");
            }
        }
    };
    public static void insertOne(Document document,MongoCollection<Document> collection){
        collection.insertOne(document,src);
    }

    public static void insertMany(List<Document> documents, MongoCollection<Document> collection){
        collection.insertMany(documents,src);
    }
}