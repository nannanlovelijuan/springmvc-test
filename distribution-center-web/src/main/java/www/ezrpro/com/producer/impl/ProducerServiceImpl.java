package www.ezrpro.com.producer.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import www.ezrpro.com.producer.ProducerService;
import www.ezrpro.com.util.KafkaUtil;

import java.util.Random;

/**
 * @author liyuelin
 * @Date 2018/12/21
 */
@Service
public class ProducerServiceImpl implements ProducerService {

    private Logger logger = Logger.getLogger(ProducerServiceImpl.class);

    @Autowired
    private KafkaTemplate<String,String> template;

    //发送消息方法
    public void sendJson(String topic, String json) {

        JSONObject jsonObj = JSON.parseObject(json);

        jsonObj.put("topic", topic);
        jsonObj.put("ts", System.currentTimeMillis() + "");
        if (KafkaUtil.createTopic(topic)){
            String key = String.valueOf(new Random().nextInt(9));
            ListenableFuture<SendResult<String, String>> future = template.send(topic, key,jsonObj.toJSONString());
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onSuccess(SendResult<String, String> result) {
                    logger.info("msg OK." + result.toString());
                }

                @Override
                public void onFailure(Throwable ex) {
                    logger.warn("msg send failed: ");
                }
            });
        }else{
            logger.error("主题创建失败！");
        }
    }
}
