package www.ezrpro.com.producer.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import www.ezrpro.com.producer.ProducerService;
import www.ezrpro.com.util.KafkaUtil;
import www.ezrpro.com.util.MongoClientSingleton;
import www.ezrpro.com.util.MongoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author liyuelin
 * @Date 2018/12/21
 */
@Service
public class ProducerServiceImpl implements ProducerService {

    private Logger logger = Logger.getLogger(ProducerServiceImpl.class);
    private List<Document> docs = new ArrayList<Document>();
    private final MongoClient mongoClient = MongoClientSingleton.INSTANCE.getMongoClient();
    MongoDatabase mongoDatabase = mongoClient.getDatabase("ezp-bigdata-log");
    MongoCollection<Document> collectionError = mongoDatabase.getCollection("webApiLogError");
    MongoCollection<Document> collectionInfo = mongoDatabase.getCollection("webApiLogInfo");

    @Autowired
    private KafkaTemplate<String,String> template;

    //发送消息方法
    public void sendJson(String topic,String json) {

        final JSONObject jsonObj = JSON.parseObject(json);

        jsonObj.put("topic", topic);
        jsonObj.put("timeMillis", System.currentTimeMillis());
        jsonObj.put("data",json);
        if (KafkaUtil.createTopic(topic)){
            String key = String.valueOf(new Random().nextInt(9));
            ListenableFuture<SendResult<String, String>> future = template.send(topic, key,jsonObj.toJSONString());
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    jsonObj.put("state","消息发送成功");
                    jsonObj.put("id",UUID.randomUUID().toString());
                    Document document = new Document(jsonObj);
                    docs.add(document);
                    if (docs.size()==100){
                        MongoUtil.insertMany(docs,collectionInfo);
                        docs.clear();
                    }
                }

                @Override
                public void onFailure(Throwable ex) {
                    jsonObj.put("state","消息发送失败");
                    jsonObj.put("id",UUID.randomUUID().toString());
                    Document document = new Document(jsonObj);
                    MongoUtil.insertOne(document, collectionError);
                }
            });
        }else{
            logger.error("主题创建失败！");
        }
    }
}
