package www.ezrpro.com.model;

import lombok.Getter;
import lombok.Setter;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  16:24:27
* 
*/

@Getter
@Setter
public class Signature{

    /**
     * 数据中心分配的应用id;
     */
    private String appId;

    /**
     * 数据中心分配的签名密钥,需要保密
     */
    private String appSecret;

    /**
     * 当前时间戳,单位为秒
     */
    private int timestamp;

    /**
     * 随机字符串标识,不超过32个字符
     */
    private String nonce;

    /**
     * 签名算法,支持sha256,后续支持各种
     */
    private String sign;

    /**
     * 最终的签名参数
     */
    private String signature;

}