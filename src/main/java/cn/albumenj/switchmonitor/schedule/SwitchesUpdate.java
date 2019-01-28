package cn.albumenj.switchmonitor.schedule;

import cn.albumenj.switchmonitor.bean.SwitchesList;
import cn.albumenj.switchmonitor.service.*;
import cn.albumenj.switchmonitor.util.CustomThreadFactory;
import cn.albumenj.switchmonitor.util.SnmpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
        SnmpUtil snmpUtil = new SnmpUtil();
        SwitchUpdate.getSwitchesStatusHistories().clear();
        SwitchUpdate.getSwitchesStatuses().clear();
        switchUpdate.updatePortStatusMap();
        switchUpdate.updatePortSpeedMap();
        switchUpdate.updateSpeedBlankMap();
        switchUpdate.setSnmpUtil(snmpUtil);

        List<SwitchesList> switchesLists = switchesListService.selectOnline(new SwitchesList());
        ExecutorService executorService = new ThreadPoolExecutor(1000,1000,5,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(),new CustomThreadFactory());
        for(SwitchesList s:switchesLists) {
            executorService.execute(() -> {
                switchUpdate.submit(s);
            });
        }
        executorService.shutdown();
        try {//等待直到所有任务完成
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            logger.warn("SwitchesUpdate Interrupted!");
        }
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
