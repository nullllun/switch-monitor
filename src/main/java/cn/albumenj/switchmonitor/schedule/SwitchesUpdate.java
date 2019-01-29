package cn.albumenj.switchmonitor.schedule;

import cn.albumenj.switchmonitor.bean.SwitchesList;
import cn.albumenj.switchmonitor.service.*;
import cn.albumenj.switchmonitor.util.CustomExecutors;
import cn.albumenj.switchmonitor.util.SnmpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 交换机信息刷新
 *
 * @author Albumen
 */
@Component
public class SwitchesUpdate {
    private final static Logger logger = LoggerFactory.getLogger(SwitchesUpdate.class);
    @Autowired
    SwitchesListService switchesListService;
    @Autowired
    SwitchUpdate switchUpdate;
    @Autowired
    SwitchesStatusHistoryService switchesStatusHistoryService;
    @Autowired
    SwitchesStatusService switchesStatusService;
    @Autowired
    PortStatusService portStatusService;
    @Autowired
    PortSpeedHistoryService portSpeedHistoryService;

    public void execute() {
        Long time = System.currentTimeMillis();
        SnmpUtil snmpUtil = new SnmpUtil();
        ExecutorService executorService = CustomExecutors.newFixExecutorService(4);
        executorService.execute(() -> {
            SwitchUpdate.getSwitchesStatusHistories().clear();
        });
        executorService.execute(() -> {
            SwitchUpdate.getSwitchesStatuses().clear();
        });
        executorService.execute(() -> {
            switchUpdate.updatePortStatusMap();
        });
        executorService.execute(() -> {
            switchUpdate.updateSpeedBlankMap();
        });
        CustomExecutors.waitExecutor(executorService);

        switchUpdate.setSnmpUtil(snmpUtil);

        List<SwitchesList> switchesLists = switchesListService.selectOnline(new SwitchesList());
        executorService = CustomExecutors.newFixExecutorService(switchesLists.size());
        for(SwitchesList s:switchesLists) {
            executorService.execute(() -> {
                switchUpdate.submit(s);
            });
        }
        CustomExecutors.waitExecutor(executorService);

        System.out.println(System.currentTimeMillis() - time);
        synchronized (SwitchUpdate.getSwitchesStatusHistories()) {
            if(SwitchUpdate.getSwitchesStatusHistories().size()>0) {
                switchesStatusHistoryService.insertList(SwitchUpdate.getSwitchesStatusHistories());
            }
            SwitchUpdate.getSwitchesStatusHistories().clear();
        }
        synchronized (SwitchUpdate.getSwitchesStatuses()) {
            if(SwitchUpdate.getSwitchesStatuses().size()>0) {
                switchesStatusService.updateList(SwitchUpdate.getSwitchesStatuses());
            }
            SwitchUpdate.getSwitchesStatuses().clear();
        }
        snmpUtil.close();
    }

}
