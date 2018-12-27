package www.ezrpro.com.interceptor;


import org.bson.Document;

import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import www.ezrpro.com.security.SignVerify;
import www.ezrpro.com.util.MongoClientSingleton;
import www.ezrpro.com.util.MongoUtil;

/**
 * 
 * @auth: nanChen
 * @date: 2018-12-24 16:09:56
 * 
 */

public class CommonInterceptor implements HandlerInterceptor{

    private final MongoClient mongoClient = MongoClientSingleton.INSTANCE.getMongoClient();

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object handler) throws Exception {
        String appId = httpServletRequest.getHeader("appId");
        String timestamp = httpServletRequest.getHeader("timestamp");
        String nonce = httpServletRequest.getHeader("nonce");
        String sign = httpServletRequest.getHeader("sign");
        String signature = httpServletRequest.getHeader("signature");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appId", appId);
        jsonObject.put("timestamp", timestamp);
        jsonObject.put("nonce", nonce);
        jsonObject.put("sign", sign);
        jsonObject.put("signature", signature);
        jsonObject.put("id",UUID.randomUUID().toString());

        MongoDatabase mongoDatabase = mongoClient.getDatabase("ezp-bigdata-log");
        MongoCollection<Document> collectionError = mongoDatabase.getCollection("webApiLogInterceptorError");
        MongoCollection<Document> collectionInfo = mongoDatabase.getCollection("webApiLogInterceptorInfo");
        
        boolean flag = (StringUtils.isNotBlank(appId))||(!StringUtils.isNumeric(timestamp))||(StringUtils.isNotBlank(nonce))||(StringUtils.isNotBlank(signature));
        if(!flag){
            jsonObject.put("msg", "请求参数不合法");
            Document document = new Document(jsonObject);
            MongoUtil.insertOne(document, collectionError);
            return false;
        }

        boolean verifySign = SignVerify.verifySign(appId, Integer.valueOf(timestamp) , nonce, sign, signature);
        if(!verifySign){
            jsonObject.put("msg", "签名验证不通过");
            Document document = new Document(jsonObject);
            MongoUtil.insertOne(document, collectionError);
            return false;
        }

        jsonObject.put("msg", "有效的签名");
        Document document = new Document(jsonObject);
        MongoUtil.insertOne(document, collectionInfo);
        return verifySign;

    }

    @Override
    public void postHandle(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}