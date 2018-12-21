package www.ezrpro.com.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * 
 * @auth: nanChen
 * @date: 2018-12-21 16:36:17
 * 
 */

public class SignVerify{



    /**
     * 签名验证
     * @param appId
     * @param timestamp
     * @param nonce
     * @param sign
     * @return
     */
    public static boolean verifySign(String appId,int timestamp,String nonce,String sign,String signature){
        
        String conStr = "app_id=" + appId + "&nonce=" + nonce + "&sign=" + sign + "&timestamp=" + timestamp;
        String secret = "abcdef";
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
}