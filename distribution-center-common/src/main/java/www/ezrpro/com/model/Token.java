package www.ezrpro.com.model;

import lombok.Data;

@Data
public class Token{
    private int appId;
    private long signTime;
    private long expireTime;
    private String token;
}