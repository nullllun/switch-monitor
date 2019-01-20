package cn.albumenj.switchmonitor.schedule;

import cn.albumenj.switchmonitor.bean.SwitchesList;
import cn.albumenj.switchmonitor.service.*;
import cn.albumenj.switchmonitor.util.CustomThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;

@Component
public class SwitchesUpdate {
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
        SwitchUpdate.getSwitchesStatusHistories().clear();
        SwitchUpdate.getSwitchesStatuses().clear();
        switchUpdate.updatePortStatusMap();

        List<SwitchesList> switchesLists = switchesListService.select(new SwitchesList());
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
            e.printStackTrace();
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
    }
}
