package cn.albumenj.switchmonitor.controller.web;

import cn.albumenj.switchmonitor.dto.MpLoginDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
