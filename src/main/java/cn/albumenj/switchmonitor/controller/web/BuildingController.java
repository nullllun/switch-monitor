package cn.albumenj.switchmonitor.controller.web;

import cn.albumenj.switchmonitor.bean.SwitchesList;
import cn.albumenj.switchmonitor.bean.SwitchesStatus;
import cn.albumenj.switchmonitor.dto.SwitchesStatusDto;
import cn.albumenj.switchmonitor.service.SwitchesListService;
import cn.albumenj.switchmonitor.service.SwitchesStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BuildingController {
    @Autowired
    SwitchesListService switchesListService;
    @Autowired
    SwitchesStatusService switchesStatusService;

    @RequestMapping("/api/buildings_list")
    public List<String> buildingList(){
        return switchesListService.selectBuilding();
    }

    @RequestMapping("/api/building/{building}")
    public List<SwitchesStatusDto> building(@PathVariable("building") String building){
        return switchesStatusService.selectByBuilding(building);
    }

    @RequestMapping("/api/ports")
    public String ports(){
        return "[\"172.16.101.7,GigabitEthernet1/0/49,172.16.101.7上联口\", \"172.16.101.253,XGigabitEthernet0/0/1,东一汇聚上联口\", \"172.16.254.1,Eth-Trunk1,东区Trunk\", \"172.16.254.1,Eth-Trunk2,西区Trunk\", \"172.16.254.1,Eth-Trunk3,生活区Trunk\", \"172.16.254.1,Eth-Trunk10,生活区Trunk\"]";
    }
}