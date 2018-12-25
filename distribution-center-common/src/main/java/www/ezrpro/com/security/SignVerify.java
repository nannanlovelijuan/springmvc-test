package www.ezrpro.com.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import redis.clients.jedis.Jedis;
import www.ezrpro.com.model.Signature;
import www.ezrpro.com.redis.*;

/**
 * 
 * @auth: nanChen
 * @date: 2018-12-21 16:36:17
 * 
 */

public class SignVerify{



    private final static String redisKey = "www.ezrpro.com.bigdata.webApi:";
    private final static String secretKey ="www.ezrpro.com.bigdata.secret:";
    private final static int minuts = 5;

    /**
     * 签名验证
     * @param appId
     * @param timestamp
     * @param nonce
     * @param sign
     * @return
     */
    public static boolean verifySign(String appId,int timestamp,String nonce,String sign,String signature){
        
        Signature signatureObj = getOrSetSignature(appId, minuts);

        DateTime now = new DateTime();
        
        DateTime signTime = now.plusMinutes(0-minuts);
        
        if(signTime.getMillis()/1000>timestamp){
            return false;
        }
        String conStr = "app_id=" + appId + "&nonce=" + nonce + "&sign=" + sign + "&timestamp=" + timestamp;
        String secret = signatureObj.getAppSecret();
        String ss = "";
        try {
            ss = EncryptAlgorithm.sha256_HMAC(conStr, secret);
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
        }
        return Objects.equals(signature, ss);
    }


    /**
     * redis 
     * @param appId
     * @param minuts
     * @return
     */
    private static Signature getOrSetSignature(String appId,int minuts){

        Signature signature = null;
        Jedis jedis = JedisClientFactory.getJedisClient();
        JedisLock jedisLock = new JedisLock("ezr.bd.ezsync.lock","lockkey");
        String data = jedis.get(redisKey+appId);
        if(StringUtils.isBlank(data)){
            try {
                if (jedisLock.tryLock()) {
                    signature = new Signature();
                    signature.setAppId(appId);
                    signature.setAppSecret(secretKey+appId);
                    signature.setExpireMinuts(minuts);
                    String value = JSON.toJSONString(signature);
                    jedis.set(redisKey+appId, value);
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            signature = JSONObject.parseObject(data,Signature.class);
        }
        return signature;
    }
}