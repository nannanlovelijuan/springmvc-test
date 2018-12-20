package www.ezrpro.com.util;


import com.alibaba.fastjson.JSON;

import www.ezrpro.com.model.HttpRespon;
import www.ezrpro.com.model.Token;

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

        

        HttpRespon<String> respon= jwtUtil.verifyToken(tokenStr, 1);
        System.out.println("verifgToken:"+JSON.toJSONString(respon));
    
    }

}



