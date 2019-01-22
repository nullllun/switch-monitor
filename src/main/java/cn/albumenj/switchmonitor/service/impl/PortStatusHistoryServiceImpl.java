package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.PortStatusHistory;
import cn.albumenj.switchmonitor.dao.PortStatusHistoryMapper;
import cn.albumenj.switchmonitor.service.PortStatusHistoryService;
import cn.albumenj.switchmonitor.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Albumen
 */
@Service
public class PortStatusHistoryServiceImpl implements PortStatusHistoryService{
    @Autowired
    private PortStatusHistoryMapper portStatusHistoryMapper;

    @Value("${history.port-saveTime}")
    Integer portSaveTime;

    @Override
    public int insert(PortStatusHistory portStatusHistory){
        return portStatusHistoryMapper.insert(portStatusHistory);
    }


    @Override
    public int delete() {
        PortStatusHistory portStatusHistory = new PortStatusHistory();
        portStatusHistory.setTimeStamp(DateUtil.beforeNow(portSaveTime));
        return portStatusHistoryMapper.delete(portStatusHistory);
    }

    @Override
    public List<PortStatusHistory> selectBySwitch(PortStatusHistory portStatusHistory) {
        return portStatusHistoryMapper.selectBySwitch(portStatusHistory);
    }

    @Override
    public List<PortStatusHistory> selectByPort(PortStatusHistory portStatusHistory) {
        return portStatusHistoryMapper.selectByPort(portStatusHistory);
    }

}
