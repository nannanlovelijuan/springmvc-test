package www.ezrpro.com.util;


import com.alibaba.fastjson.JSON;

import www.ezrpro.com.model.ServiceRespon;
import www.ezrpro.com.model.Token;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;

public class JwtUtilTest {
    public static void main(String[] args) {
        JwtUtil jwtUtil = new JwtUtil();

        Token token = jwtUtil.gererateToken(1);
        // System.out.println("token:"+token.getToken()+
        // "\n signTime:"+token.getSignTime()+
        // "\n expireTime:"+token.getExpireTime());

        String tokenStr = token.getToken();

        // System.out.println("token:"+JSON.toJSONString(token));
        // String tokenStr ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxIiwiaXNzIjoic2VydmljZSIsImV4cCI6MTU0NTI5NTgyOSwiaWF0IjoxNTQ1Mjk1Nzk5fQ.yQHlmQEEEXkd0ZBHd1AiqdpnyXrhWQkVOpNnvtsyyLE";

        

        ServiceRespon<String> respon= jwtUtil.verifyToken(tokenStr, 1);
        System.out.println("verifgToken:"+JSON.toJSONString(respon));

        System.out.println(getTxUrl());
    
    }


    public static String getTxUrl(){
        long timeStamp =   Integer.valueOf(String.valueOf(System.currentTimeMillis()/1000));
        String appId = "1";
        String appSecret = "abcdef";
        String nonce  =String.valueOf(new Random().nextInt(10));
        String sign = "sha256";
        String str = "app_id="+appId+"&nonce="+nonce+"&sign="+sign+"&timestamp="+timeStamp;
        String signature  = sha256_HMAC(str,appSecret);
        return "http://localhost:8081/distribution_center_web_war/send?"+str+"&signature="+signature;
    }


    /**
     *
     * @param message 字符串
     * @param secret 秘钥
     * @return 返回字符串
     */

    public static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byte2Hex(bytes);
        } catch (Exception e) {

        }
        return hash;
    }

    public static String byte2Hex(byte[] bytes){
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i=0;i<bytes.length;i++){
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}



