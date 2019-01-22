package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.SwitchesReachable;
import cn.albumenj.switchmonitor.dao.SwitchesReachableMapper;
import cn.albumenj.switchmonitor.dto.BriefStatusDto;
import cn.albumenj.switchmonitor.schedule.SwitchesBriefFetch;
import cn.albumenj.switchmonitor.service.SwitchesReachableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwitchesReachableServiceImpl implements SwitchesReachableService{
    @Autowired
    SwitchesBriefFetch switchesBriefFetch;

    @Autowired
    private SwitchesReachableMapper switchesReachableMapper;

    @Override
    public int insert(SwitchesReachable switchesReachable){
        return switchesReachableMapper.insert(switchesReachable);
    }


    @Override
    public int insertList(List<SwitchesReachable> switchesReachables){
        return switchesReachableMapper.insertList(switchesReachables);
    }

    @Override
    public int update(SwitchesReachable switchesReachable) {
        if(switchesReachableMapper.update(switchesReachable) == 0){
            return switchesReachableMapper.insert(switchesReachable);
        }
        else{
            return 1;
        }
    }

    @Override
    public int updateList(List<SwitchesReachable> switchesReachables) {
        int row = switchesReachableMapper.updateList(switchesReachables);
        if (row != switchesReachables.size()) {
            for (SwitchesReachable switchesReachable : switchesReachables) {
                row += update(switchesReachable);
            }
        }
        return row;
    }

    @Override
    public List<SwitchesReachable> select() {
        return switchesReachableMapper.select();
    }

    @Override
    public SwitchesReachable selectBySwitch(SwitchesReachable switchesReachable) {
        return switchesReachableMapper.selectBySwitch(switchesReachable);
    }

    @Override
    public BriefStatusDto fetchBrief() {
        return switchesBriefFetch.getBriefStatusDtoStat();
    }
}
