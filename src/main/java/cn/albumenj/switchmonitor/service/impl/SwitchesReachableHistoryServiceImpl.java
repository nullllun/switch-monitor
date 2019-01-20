package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.SwitchesStatusHistory;
import cn.albumenj.switchmonitor.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import cn.albumenj.switchmonitor.bean.SwitchesReachableHistory;
import cn.albumenj.switchmonitor.dao.SwitchesReachableHistoryMapper;
import cn.albumenj.switchmonitor.service.SwitchesReachableHistoryService;

@Service
public class SwitchesReachableHistoryServiceImpl implements SwitchesReachableHistoryService{
    @Value("${history.switch-reachable-saveTime}")
    Integer switchReachableSaveTime;

    @Resource
    private SwitchesReachableHistoryMapper switchesReachableHistoryMapper;

    @Override
    public int insert(SwitchesReachableHistory switchesReachableHistory){
        return switchesReachableHistoryMapper.insert(switchesReachableHistory);
    }

    @Override
    public int insertList(List<SwitchesReachableHistory> switchesReachableHistorys){
        return switchesReachableHistoryMapper.insertList(switchesReachableHistorys);
    }

    @Override
    public int delete() {
        SwitchesReachableHistory switchesReachableHistory = new SwitchesReachableHistory();
        switchesReachableHistory.setTimeStamp(DateUtil.beforeNow(switchReachableSaveTime));
        return switchesReachableHistoryMapper.delete(switchesReachableHistory);
    }

    @Override
    public List<SwitchesReachableHistory> select() {
        return switchesReachableHistoryMapper.select();
    }

    @Override
    public List<SwitchesReachableHistory> selectBySwitch(SwitchesReachableHistory switchesReachableHistory) {
        return switchesReachableHistoryMapper.selectBySwitch(switchesReachableHistory);
    }
}
