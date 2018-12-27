package www.ezrpro.com.util;

import java.util.List;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoCollection;

import org.bson.Document;
import org.apache.log4j.Logger;

/**
 * 
 * @auth: nanChen
 * @date: 2018-12-25 15:42:10
 * 
 */

public class MongoUtil{

    private static Logger logger = Logger.getLogger(MongoUtil.class);

    private static SingleResultCallback<Void> src = new SingleResultCallback<Void>() {
        @Override
        public void onResult(final Void result, final Throwable t) {
            if(t != null){
                logger.error(t.getMessage());
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