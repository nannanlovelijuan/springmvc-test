package www.ezrpro.com.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import www.ezrpro.com.util.EncodeUtil;

/**
 * 
 * @auth: nanChen
 * @date: 2018-12-21 16:45:59
 * 
 */

public class EncryptAlgorithm {

    public static String sha256_HMAC(String message, String secret) throws InvalidKeyException, NoSuchAlgorithmException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
        return EncodeUtil.byte2Hex(bytes);
    }
}