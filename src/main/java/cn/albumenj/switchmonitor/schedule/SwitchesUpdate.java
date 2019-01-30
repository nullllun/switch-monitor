package cn.albumenj.switchmonitor.schedule;

import cn.albumenj.switchmonitor.bean.Log;
import cn.albumenj.switchmonitor.bean.SwitchesList;
import cn.albumenj.switchmonitor.constant.LogConst;
import cn.albumenj.switchmonitor.service.*;
import cn.albumenj.switchmonitor.util.CustomExecutors;
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
    @Autowired
    LogService logService;

    public void execute() {
        Long time = System.currentTimeMillis();
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

        // 仅获取在线机器，防止过多的UDP发包阻塞网络
        List<SwitchesList> switchesLists = switchesListService.selectOnline(new SwitchesList());
        if (switchesLists.size() == 0) {
            return;
        }

        executorService = CustomExecutors.newFixExecutorService(switchesLists.size());
        for (SwitchesList s : switchesLists) {
            executorService.execute(() -> {
                switchUpdate.submit(s);
            });
        }
        CustomExecutors.waitExecutor(executorService);

        synchronized (SwitchUpdate.getSwitchesStatusHistories()) {
            if (SwitchUpdate.getSwitchesStatusHistories().size() > 0) {
                switchesStatusHistoryService.insertList(SwitchUpdate.getSwitchesStatusHistories());
            }
            SwitchUpdate.getSwitchesStatusHistories().clear();
        }
        synchronized (SwitchUpdate.getSwitchesStatuses()) {
            if (SwitchUpdate.getSwitchesStatuses().size() > 0) {
                switchesStatusService.updateList(SwitchUpdate.getSwitchesStatuses());
            }
            SwitchUpdate.getSwitchesStatuses().clear();
        }

        Log log = new Log(LogConst.DEBUG, LogConst.SYSTEM, "System",
                "Switches Information Update End Using " + (System.currentTimeMillis() - time) + "ms", "");
        log.setOperator("System");
        logService.insert(log);
    }

}
