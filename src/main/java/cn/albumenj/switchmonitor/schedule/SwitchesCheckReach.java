package cn.albumenj.switchmonitor.schedule;

import cn.albumenj.switchmonitor.bean.SwitchesList;
import cn.albumenj.switchmonitor.bean.SwitchesReachable;
import cn.albumenj.switchmonitor.bean.SwitchesReachableHistory;
import cn.albumenj.switchmonitor.service.SwitchesListService;
import cn.albumenj.switchmonitor.service.SwitchesReachableHistoryService;
import cn.albumenj.switchmonitor.service.SwitchesReachableService;
import cn.albumenj.switchmonitor.util.CustomThreadFactory;
import org.apache.tomcat.jni.OS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
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

    @Value("${commit.switchesReachables-update}")
    Integer switchesReachablesLimit;
    @Value("${commit.switchesReachableHistories-insert}")
    Integer switchesReachableHistoriesLimit;


    List<SwitchesReachableHistory> switchesReachableHistories = new LinkedList<>();
    private List<SwitchesReachable> switchesReachables = new CopyOnWriteArrayList<>();

    public void submit() {

        List<SwitchesList> switchesLists = switchesListService.select(new SwitchesList());
        switchesReachables.clear();

        Long time = System.currentTimeMillis();
        ExecutorService executorService = new ThreadPoolExecutor(1000, 1000, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new CustomThreadFactory());

        for (SwitchesList switchesList : switchesLists) {
            executorService.submit(() -> {
                check(switchesList, 10);
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
    }

    private void check(SwitchesList switchesList, int times) {
        try {
            boolean reachable;
            if (OS.IS_LINUX) {
                final Process process = Runtime.getRuntime().exec("ping -n 1 -w 50 " + switchesList.getIp());
                ExecutorService executorService = new ThreadPoolExecutor(2, 2, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new CustomThreadFactory());
                executorService.execute(() -> {
                    printMessage(process.getInputStream());
                });
                executorService.execute(() -> {
                    printMessage(process.getErrorStream());
                });
                executorService.shutdown();

                boolean end = process.waitFor(100, TimeUnit.MILLISECONDS);
                if (end) {
                    reachable = (0 == process.exitValue());
                } else {
                    process.destroy();
                    reachable = false;
                }
            } else {
                InetAddress inetAddress = InetAddress.getByName(switchesList.getIp());
                reachable = inetAddress.isReachable(50);
            }
            SwitchesReachable switchesReachable = new SwitchesReachable();
            if (reachable) {
                switchesReachable.setSwitchId(switchesList.getId());
                switchesReachable.setReachable(1);

                switchesReachables.add(switchesReachable);
            } else if (times == 0) {
                switchesReachable.setSwitchId(switchesList.getId());
                switchesReachable.setReachable(0);
                SwitchesReachable switchesReachableOld = switchesReachableService.selectBySwitch(switchesReachable);
                if (switchesReachableOld.getReachable() == null || switchesReachableOld.getReachable() == 1) {
                    switchesReachable.setDownTime(new Date());
                } else {
                    switchesReachable.setDownTime(switchesReachableOld.getDownTime());
                }

                switchesReachables.add(switchesReachable);
            } else {
                check(switchesList, times - 1);
            }
        } catch (InterruptedException e) {
            logger.warn("Check " + e.toString());
        } catch (IOException e) {
            logger.warn("Check " + e.toString());
        }
    }

    private static void printMessage(final InputStream input) {

        Reader reader = new InputStreamReader(input);
        BufferedReader bf = new BufferedReader(reader);
        String line = null;
        try {
            while ((line = bf.readLine()) != null) {
            }
        } catch (IOException e) {
            logger.warn("PrintPingMessage " + e.toString());
        }
    }


}
