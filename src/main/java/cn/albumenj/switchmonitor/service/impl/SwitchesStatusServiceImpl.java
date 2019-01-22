package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.SwitchesList;
import cn.albumenj.switchmonitor.bean.SwitchesStatus;
import cn.albumenj.switchmonitor.constant.StatusConst;
import cn.albumenj.switchmonitor.dao.SwitchesStatusMapper;
import cn.albumenj.switchmonitor.dto.BriefStatusDto;
import cn.albumenj.switchmonitor.dto.SwitchesStatusDto;
import cn.albumenj.switchmonitor.schedule.SwitchesBriefFetch;
import cn.albumenj.switchmonitor.service.SwitchesListService;
import cn.albumenj.switchmonitor.service.SwitchesStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Albumen
 */
@Service
public class SwitchesStatusServiceImpl implements SwitchesStatusService{
    @Autowired
    SwitchesBriefFetch switchesBriefFetch;

    @Autowired
    private SwitchesStatusMapper switchesStatusMapper;

    @Autowired
    private SwitchesListService switchesListService;

    @Override
    public int insert(SwitchesStatus switchesStatus){
        return switchesStatusMapper.insert(switchesStatus);
    }

    @Override
    public int update(SwitchesStatus switchesStatus){
        if(switchesStatusMapper.update(switchesStatus)==0){
            return switchesStatusMapper.insert(switchesStatus);
        } else{
            return 1;
        }
    }

    @Override
    public int updateList(List<SwitchesStatus> switchesStatuses) {
        int row = switchesStatusMapper.updateList(switchesStatuses);
        if (row != switchesStatuses.size()) {
            for (SwitchesStatus switchesStatus : switchesStatuses) {
                row += update(switchesStatus);
            }
        }
        return row;
    }

    @Override
    public int delete(SwitchesStatus switchesStatus) {
        return switchesStatusMapper.delete(switchesStatus);
    }

    @Override
    public List<SwitchesStatus> select() {
        return switchesStatusMapper.select();
    }

    @Override
    public List<SwitchesStatus> selectBySwitch(SwitchesStatus switchesStatus) {
        return switchesStatusMapper.selectBySwitch(switchesStatus);
    }

    @Override
    public BriefStatusDto fetchBriefDetail(Integer mode) {
        switch (mode){
            case StatusConst.CPU_LOAD:
                return switchesBriefFetch.getBriefStatusDtoCpu();
            case StatusConst.MEM_USED:
                return switchesBriefFetch.getBriefStatusDtoMem();
            case StatusConst.TEMP:
                return switchesBriefFetch.getBriefStatusDtoTemp();
            case StatusConst.WARNING:
                return switchesBriefFetch.getBriefStatusDtoWarn();
            default:
                return null;
        }
    }

    @Override
    public List<SwitchesStatusDto> selectByBuilding(String building) {
        SwitchesList switchesList = new SwitchesList();
        switchesList.setBuilding(building);
        List<SwitchesStatusDto> switchesStatusDtos = switchesStatusMapper.selectByBuilding(switchesList);
        for(SwitchesStatusDto switchesStatusDto:switchesStatusDtos){
            if("-2".equals(switchesStatusDto.getTemp())){
                switchesStatusDto.setTemp("设备不支持");
            }
            if("-2".equals(switchesStatusDto.getCpuLoad())){
                switchesStatusDto.setCpuLoad("设备不支持");
            }
            if("-2".equals(switchesStatusDto.getMemUsed())){
                switchesStatusDto.setMemUsed("设备不支持");
            }
            if(switchesStatusDto.getReachable().equals(1)){
                switchesStatusDto.setDownTime(0L);
            }
        }
        return switchesStatusDtos;
    }

}
