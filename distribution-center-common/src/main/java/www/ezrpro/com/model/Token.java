package www.ezrpro.com.model;

import lombok.Data;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  10:55:50
* 
*/
@Data
public class Token{

    /**
     * 应用Id
     */
    private int appId;

    /**
     * 注册时间
     */
    private long signTime;

    /**
     * 过期时间
     */
    private long expireTime;

    /**
     * token
     */
    private String token;
}