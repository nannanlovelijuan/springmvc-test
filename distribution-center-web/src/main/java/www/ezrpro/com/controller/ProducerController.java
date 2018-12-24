package www.ezrpro.com.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import www.ezrpro.com.model.ClientRequest;
import www.ezrpro.com.model.ServiceRespon;
import www.ezrpro.com.producer.ProducerService;
import www.ezrpro.com.security.SignVerify;
import www.ezrpro.com.util.KafkaUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liyuelin
 * @Date 2018/12/21
 */
@Controller
public class ProducerController {

    @Autowired ProducerService producerService;

    @RequestMapping(value = "/send",method = RequestMethod.POST)
    @ResponseBody
    public ServiceRespon sendMsg(@RequestParam("timestamp") int timestamp,
                                 @RequestParam("appId") String appId,
                                 @RequestParam("nonce") String nonce,
                                 @RequestParam("sign") String sign,
                                 @RequestParam("signature") String signature,
                                 HttpServletRequest request
            ){
        String data = request.getParameter("data");
        ClientRequest clientRequest = JSON.parseObject(data,ClientRequest.class);
        boolean verification = SignVerify.verifySign(appId,timestamp,nonce,sign,signature);

        String dataJson = clientRequest.getData();
        ServiceRespon serviceRespon = new ServiceRespon();
        if (!verification){
            serviceRespon.setStatus(false);
            serviceRespon.setStatusCode(401);
            serviceRespon.setMsg("验证失败！");
            return  serviceRespon;
        }else if (clientRequest.getIdType()!= 1 && clientRequest.getIdType()!=2){
            serviceRespon.setStatus(false);
            serviceRespon.setStatusCode(401);
            serviceRespon.setMsg("idtype = "+clientRequest.getIdType()+"\t"+"不符合规则");
            return serviceRespon;
        }else if (dataJson == null){
            serviceRespon.setStatus(false);
            serviceRespon.setStatusCode(401);
            serviceRespon.setMsg("消息数据为空！");
            return  serviceRespon;
        }
        String topic = KafkaUtil.getTopic(clientRequest.getId(),clientRequest.getIdType(),clientRequest.getAppId(),clientRequest.getTableName());

        producerService.sendJson(topic,dataJson);
        serviceRespon.setMsg("消息正在发送");
        serviceRespon.setStatus(true);
        serviceRespon.setStatusCode(200);
        return serviceRespon;
    }
}
