package cn.albumenj.switchmonitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 通用
 *
 * @author Albumen
 */
@Controller
public class CommonController {
    @RequestMapping(path = "/api/login", method = RequestMethod.POST)
    public String login() {
        return "redirect:/home_page.html";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String homePost() {
        return "redirect:/web/index.html";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homeGet() {
        return "redirect:/web/index.html";
    }
}
