package cn.albumenj.switchmonitor;

import cn.albumenj.switchmonitor.schedule.HistoryClean;
import cn.albumenj.switchmonitor.service.PortSpeedHistoryService;
import cn.albumenj.switchmonitor.service.PortStatusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SwitchMonitorApplicationTests {
    @Autowired
    PortStatusService portStatusService;

    @Test
    public void contextLoads() {
        portStatusService.updateVlan();
    }

}

