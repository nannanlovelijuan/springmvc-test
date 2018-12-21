package www.ezrpro.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import www.ezrpro.com.App;

/**
* 
* @auth: nanChen
* @date: 2018-12-21  11:00:27
* 
*/

@Controller
public class MainController {
    
    @RequestMapping("/index")
    public String index(){
        App.test();
        System.out.println("method:index");
        return "index";
    }


}