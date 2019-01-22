package cn.albumenj.switchmonitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {
    @RequestMapping(path = "/api/login",method = RequestMethod.POST)
    public String login(){
        return "redirect:/home_page.html";
    }
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public String homePost(){
        return "redirect:/home_page.html";
    }
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String homeGet(){
        return "redirect:/home_page.html";
    }
}
