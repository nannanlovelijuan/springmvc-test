package www.ezrpro.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import www.ezrpro.com.App;


@Controller
public class MainController {
    
    @RequestMapping("/index")
    public String index(){
        App.test();
        System.out.println("method:index");
        return "index";
    }


}