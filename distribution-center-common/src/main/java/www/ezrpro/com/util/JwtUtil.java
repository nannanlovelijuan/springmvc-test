package www.ezrpro.com.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;

import www.ezrpro.com.model.ServiceRespon;
import www.ezrpro.com.model.Token;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  10:58:02
* 
*/

public class JwtUtil {

   
    /** token 过期时间: 10分钟 */
	public  int calendarField = Calendar.SECOND;
    public  int calendarInterval = 30;
    public  String secret = "msgCenter";

    public Token gererateToken(int appId){
         /** token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj */
        String ss = secret+appId;

        Token token = new Token();

        Date signtDate = new Date();
		// expire time
		Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
		Date expiresDate = nowTime.getTime();
        
		// header Map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("alg", "HS256");
        map.put("typ", "JWT");
        
        String tokenStr = JWT
        .create()
        .withHeader(map)
        .withClaim("iss", "service")
        .withClaim("aud", String.valueOf(appId))
        .withIssuedAt(signtDate) // sign time
        .withExpiresAt(expiresDate) // expire time
        .sign(Algorithm.HMAC256(ss)); // signatur

        token.setAppId(appId);

        //jwt 将Date类型转换为毫秒级的时候后三位变为了0
        token.setSignTime(Long.valueOf(signtDate.getTime()/1000+"000"));
        token.setExpireTime(Long.valueOf(expiresDate.getTime()/1000+"000"));
        token.setToken(tokenStr);
        return token;
    }


    public ServiceRespon<String> verifyToken(String tokenStr,int appId){
        String ss = secret+appId;
        ServiceRespon<String> respon = new ServiceRespon<String>();
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(ss)).build();
            verifier.verify(tokenStr);
            respon.setStatusCode(200);
            respon.setStatus(true);
            respon.setMsg("有效的Token:"+tokenStr);
        } catch (TokenExpiredException e) {
            respon.setStatusCode(401);
            respon.setStatus(false);
            respon.setMsg("过期的Token:"+tokenStr);
            e.printStackTrace();
        } catch(Exception e){
            respon.setStatusCode(401);
            respon.setStatus(false);
            respon.setMsg("无效的Token:"+tokenStr);
            e.printStackTrace();
        }
        
        return respon;

    }

}