package cn.albumenj.switchmonitor.schedule;

import cn.albumenj.switchmonitor.bean.SwitchesList;
import cn.albumenj.switchmonitor.bean.SwitchesReachable;
import cn.albumenj.switchmonitor.bean.SwitchesReachableHistory;
import cn.albumenj.switchmonitor.service.SwitchesListService;
import cn.albumenj.switchmonitor.service.SwitchesReachableHistoryService;
import cn.albumenj.switchmonitor.service.SwitchesReachableService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SwitchesCheckReach {
    @Autowired
    SwitchesReachableService switchesReachableService;
    @Autowired
    SwitchesReachableHistoryService switchesReachableHistoryService;
    @Autowired
    SwitchesListService switchesListService;

    @Value("${commit.switchesReachables-update}")
    Integer switchesReachablesLimit;
    @Value("${commit.switchesReachableHistories-insert}")
    Integer switchesReachableHistoriesLimit;

    List<SwitchesReachableHistory> switchesReachableHistories = new LinkedList<>();
    List<SwitchesReachable> switchesReachables = new LinkedList<>();

    public void submit(){
        List<SwitchesList> switchesLists = switchesListService.select(new SwitchesList());
        for(SwitchesList switchesList:switchesLists){
            check(switchesList);
        }
        if(switchesReachableHistories.size()>0){
            switchesReachableHistoryService.insertList(switchesReachableHistories);
            switchesReachableHistories.clear();
        }
        if(switchesReachables.size()>0){
            switchesReachableService.updateList(switchesReachables);
            switchesReachables.clear();
        }
    }

    public void check(SwitchesList switchesList){
        try {
            InetAddress inetAddress = InetAddress.getByName(switchesList.getIp());
            boolean reachable = inetAddress.isReachable(50);
            reachable = reachable || inetAddress.isReachable(50);
            reachable = reachable || inetAddress.isReachable(50);
            SwitchesReachable switchesReachable = new SwitchesReachable();
            SwitchesReachableHistory switchesReachableHistory = new SwitchesReachableHistory();
            if(reachable){
                switchesReachable.setSwitchId(switchesList.getId());
                switchesReachable.setReachable(1);

                BeanUtils.copyProperties(switchesReachable,switchesReachableHistory);
                switchesReachables.add(switchesReachable);
                switchesReachableHistories.add(switchesReachableHistory);
            }
            else{
                switchesReachable.setSwitchId(switchesList.getId());
                switchesReachable.setReachable(0);
                SwitchesReachable switchesReachableOld = switchesReachableService.selectBySwitch(switchesReachable);
                if(switchesReachableOld.getReachable() == 1){
                    switchesReachable.setDownTime(new Date());
                }

                BeanUtils.copyProperties(switchesReachable,switchesReachableHistory);
                switchesReachables.add(switchesReachable);
                switchesReachableHistories.add(switchesReachableHistory);
            }
        } catch (UnknownHostException e) {
            //TODO: 日志
        } catch (IOException e) {
            //TODO: 日志
        }
        if(switchesReachables.size()>switchesReachablesLimit){
            switchesReachableService.updateList(switchesReachables);
            switchesReachables.clear();
        }
        if(switchesReachableHistories.size()>switchesReachableHistoriesLimit){
            switchesReachableHistoryService.insertList(switchesReachableHistories);
            switchesReachableHistories.clear();
        }
    }
}
