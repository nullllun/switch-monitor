package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.SwitchesReachableHistory;
import cn.albumenj.switchmonitor.dao.SwitchesReachableHistoryMapper;
import cn.albumenj.switchmonitor.service.SwitchesReachableHistoryService;
import cn.albumenj.switchmonitor.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Albumen
 */
@Service
public class SwitchesReachableHistoryServiceImpl implements SwitchesReachableHistoryService {
    @Value("${history.switch-reachable-saveTime}")
    Integer switchReachableSaveTime;

    @Autowired
    private SwitchesReachableHistoryMapper switchesReachableHistoryMapper;

    @Override
    public int insert(SwitchesReachableHistory switchesReachableHistory) {
        return switchesReachableHistoryMapper.insert(switchesReachableHistory);
    }

    @Override
    public int insertList(List<SwitchesReachableHistory> switchesReachableHistorys) {
        int ret = switchesReachableHistoryMapper.insertList(switchesReachableHistorys);
        if (ret != switchesReachableHistorys.size()) {
            for (SwitchesReachableHistory switchesReachableHistory : switchesReachableHistorys) {
                ret += switchesReachableHistoryMapper.insert(switchesReachableHistory);
            }
        }
        return ret;
    }

    @Override
    public int delete() {
        SwitchesReachableHistory switchesReachableHistory = new SwitchesReachableHistory();
        switchesReachableHistory.setTimeStamp(DateUtil.beforeNowDate(switchReachableSaveTime));
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
