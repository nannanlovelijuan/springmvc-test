package www.ezrpro.com.interceptor;


import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import www.ezrpro.com.security.SignVerify;

/**
 * 
 * @auth: nanChen
 * @date: 2018-12-24 16:09:56
 * 
 */

public class CommonInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object handler) throws Exception {
    
        //每次有一个新的业务线过来，需要在redis里set对应的信息：hmset mapApp_1 appId 1 secret bigdataAppId1 expire 5
        String appId = httpServletRequest.getHeader("appId");
        String timestamp = httpServletRequest.getHeader("timestamp");
        String nonce = httpServletRequest.getHeader("nonce");
        String sign = httpServletRequest.getHeader("sign");
        String signature = httpServletRequest.getHeader("signature");


        boolean flag = (StringUtils.isBlank(appId))||(!StringUtils.isNumeric(timestamp))||(StringUtils.isBlank(nonce))||(StringUtils.isBlank(signature));
        if(flag){
            return false;
        }
        
        boolean verifySign = SignVerify.verifySign(appId, Integer.valueOf(timestamp) , nonce, sign, signature);
        if(!verifySign){
            return false;
        }
        
        return true;
    }

    @Override
    public void postHandle(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}