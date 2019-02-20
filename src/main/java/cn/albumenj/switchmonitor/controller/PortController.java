package cn.albumenj.switchmonitor.controller;

import cn.albumenj.switchmonitor.constant.PageCodeEnum;
import cn.albumenj.switchmonitor.dto.PageCodeDto;
import cn.albumenj.switchmonitor.dto.PortFlowDto;
import cn.albumenj.switchmonitor.dto.VlanSearchDto;
import cn.albumenj.switchmonitor.service.PortSpeedHistoryService;
import cn.albumenj.switchmonitor.service.PortStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 端口
 *
 * @author Albumen
 */
@RestController
public class PortController {
    @Autowired
    PortSpeedHistoryService portSpeedHistoryService;
    @Autowired
    PortStatusService portStatusService;

    @RequestMapping("/api/flow_history/{ip}/{port}")
    public List<PortFlowDto> flowHistory(@PathVariable("ip") String ip, @PathVariable("port") String port) {
        port = port.replaceAll("_", "/");
        return portSpeedHistoryService.selectFlow(ip, port);
    }

    @RequestMapping("/api/vlan/{cvlan}/{pvlan}")
    public PageCodeDto searchVlan(@PathVariable("cvlan") Integer cvlan, @PathVariable("pvlan") Integer pvlan) {
        VlanSearchDto vlanSearchDto = portStatusService.selectVlan(cvlan, pvlan);
        PageCodeDto pageCodeDto;
        if (vlanSearchDto != null) {
            pageCodeDto = new PageCodeDto();
            pageCodeDto.setObject(vlanSearchDto);
        } else {
            pageCodeDto = new PageCodeDto(PageCodeEnum.INPUT_ERROR);
        }
        return pageCodeDto;
    }
}
