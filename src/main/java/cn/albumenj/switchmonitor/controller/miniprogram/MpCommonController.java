package cn.albumenj.switchmonitor.controller.miniprogram;

import cn.albumenj.switchmonitor.dto.MpLoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MpCommonController {
    @RequestMapping(path = "/mpapi/login",method = RequestMethod.POST)
    public MpLoginDto mplogin(){
        MpLoginDto mpLoginDto = new MpLoginDto();
        mpLoginDto.setSuccess(true);
        mpLoginDto.setToken("111");
        return mpLoginDto;
    }
}
