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

        ServiceRespon serviceRespon = new ServiceRespon();
        if (clientRequest.getIdType()!= 1 || clientRequest.getIdType()!=2){
            serviceRespon.setStatus(false);
            serviceRespon.setStatusCode(401);
            serviceRespon.setMsg("idtype = "+clientRequest.getIdType()+"\t不符合规则");
        }
        String topic = KafkaUtil.getTopic(clientRequest.getId(),clientRequest.getIdType(),clientRequest.getAppId(),clientRequest.getTableName());
        String dataJson = clientRequest.getData();
        producerService.sendJson(topic,dataJson);
        System.out.println(topic);
        return serviceRespon;
    }
}
