package cn.albumenj.switchmonitor.schedule;

import cn.albumenj.switchmonitor.bean.Log;
import cn.albumenj.switchmonitor.bean.SwitchesList;
import cn.albumenj.switchmonitor.bean.SwitchesReachable;
import cn.albumenj.switchmonitor.bean.SwitchesReachableHistory;
import cn.albumenj.switchmonitor.constant.LogConst;
import cn.albumenj.switchmonitor.constant.SystemConst;
import cn.albumenj.switchmonitor.service.LogService;
import cn.albumenj.switchmonitor.service.SwitchesListService;
import cn.albumenj.switchmonitor.service.SwitchesReachableHistoryService;
import cn.albumenj.switchmonitor.service.SwitchesReachableService;
import cn.albumenj.switchmonitor.util.CustomThreadFactory;
import cn.albumenj.switchmonitor.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 交换机在线状态检测
 *
 * @author Albumen
 */
@Component
public class SwitchesCheckReach {
    private final static Logger logger = LoggerFactory.getLogger(SwitchesCheckReach.class);
    @Autowired
    SwitchesReachableService switchesReachableService;
    @Autowired
    SwitchesReachableHistoryService switchesReachableHistoryService;
    @Autowired
    SwitchesListService switchesListService;
    @Autowired
    SwitchesBriefFetch switchesBriefFetch;
    @Autowired
    SystemConst systemConst;
    @Autowired
    LogService logService;

    @Value("${commit.switchesReachables-update}")
    Integer switchesReachablesLimit;
    @Value("${commit.switchesReachableHistories-insert}")
    Integer switchesReachableHistoriesLimit;


    List<SwitchesReachableHistory> switchesReachableHistories = new LinkedList<>();
    private List<SwitchesReachable> switchesReachables = new CopyOnWriteArrayList<>();

    public void submit() {
        Long time = System.currentTimeMillis();

        List<SwitchesList> switchesLists = switchesListService.select(new SwitchesList());
        switchesReachables.clear();

        ExecutorService executorService = new ThreadPoolExecutor(1000, 1000, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new CustomThreadFactory());

        for (SwitchesList switchesList : switchesLists) {
            executorService.submit(() -> {
                check(switchesList);
            });
        }
        executorService.shutdown();
        try {
            //等待直到所有任务完成
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (switchesReachableHistories.size() > 0) {
            switchesReachableHistoryService.insertList(switchesReachableHistories);
            switchesReachableHistories.clear();
        }

        List<SwitchesReachable> switchesReachableSubmit = new LinkedList<>();
        for (SwitchesReachable switchesReachable : switchesReachables) {
            if (switchesReachable == null) {
                continue;
            }
            switchesReachableSubmit.add(switchesReachable);
            if (switchesReachableSubmit.size() > switchesReachablesLimit) {
                switchesReachableService.updateList(switchesReachableSubmit);
                switchesReachableSubmit.clear();
            }
        }
        if (switchesReachableSubmit.size() > 0) {
            switchesReachableService.updateList(switchesReachableSubmit);
            switchesReachableSubmit.clear();
        }
        switchesBriefFetch.refresh();

        Log log = new Log(LogConst.DEBUG, LogConst.SYSTEM, "System",
                "Switches Reach Check End Using " + (System.currentTimeMillis() - time) + "ms", "");
        log.setOperator("System");
        logService.insert(log);
    }

    private void check(SwitchesList switchesList) {
        try {
            boolean reachable;
            if (systemConst.isLinux()) {
                reachable = IpUtil.execPingCommand(switchesList.getIp());
            } else {
                InetAddress inetAddress = InetAddress.getByName(switchesList.getIp());
                reachable = inetAddress.isReachable(50);
            }
            SwitchesReachable switchesReachable = new SwitchesReachable();
            if (reachable) {
                switchesReachable.setSwitchId(switchesList.getId());
                switchesReachable.setReachable(1);

                switchesReachables.add(switchesReachable);
            } else {
                switchesReachable.setSwitchId(switchesList.getId());
                switchesReachable.setReachable(0);
                SwitchesReachable switchesReachableOld = switchesReachableService.selectBySwitch(switchesReachable);
                if (switchesReachableOld.getReachable() == null || switchesReachableOld.getReachable() == 1) {
                    switchesReachable.setDownTime(new Date());
                } else {
                    switchesReachable.setDownTime(switchesReachableOld.getDownTime());
                }

                switchesReachables.add(switchesReachable);
            }
        } catch (IOException e) {
            logger.warn("Check " + e.toString());
        }
    }

}
