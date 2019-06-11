package cn.albumenj.switchmonitor.controller;

import cn.albumenj.switchmonitor.dto.SwitchesStatusDto;
import cn.albumenj.switchmonitor.service.SwitchesListService;
import cn.albumenj.switchmonitor.service.SwitchesStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 楼栋
 *
 * @author Albumen
 */
@RestController
public class BuildingController {
    @Autowired
    SwitchesListService switchesListService;
    @Autowired
    SwitchesStatusService switchesStatusService;

    @RequestMapping("/api/buildings_list")
    public List<String> buildingList() {
        return switchesListService.selectBuilding();
    }

    @RequestMapping("/api/building/{building}")
    public List<SwitchesStatusDto> building(@PathVariable("building") String building) {
        return switchesStatusService.selectByBuilding(building);
    }

    @RequestMapping("/api/ports")
    public String ports() {
        return "[\"172.16.254.1,Eth-Trunk1,东区Trunk\", \"172.16.254.1,Eth-Trunk2,西区Trunk\", \"172.16.254.1,Eth-Trunk3,大学城生活区Trunk\", \"172.16.254.1,Eth-Trunk10,大学城生活区Trunk\", \"172.16.254.5,Eth-Trunk10,龙洞Trunk\", \"172.16.254.9,Eth-Trunk10,东风路Trunk\"]";
    }
}
