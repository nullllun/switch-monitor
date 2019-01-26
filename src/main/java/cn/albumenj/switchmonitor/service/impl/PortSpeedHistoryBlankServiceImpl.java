package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.PortSpeedHistoryBlank;
import cn.albumenj.switchmonitor.dao.PortSpeedHistoryBlankMapper;
import cn.albumenj.switchmonitor.service.PortSpeedHistoryBlankService;
import cn.albumenj.switchmonitor.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Albumen
 */
@Service
public class PortSpeedHistoryBlankServiceImpl implements PortSpeedHistoryBlankService {

    @Autowired
    private PortSpeedHistoryBlankMapper portSpeedHistoryBlankMapper;

    @Value("${history.port-speed-saveTime}")
    Integer portSpeedSaveTime;

    @Override
    public int insert(PortSpeedHistoryBlank portSpeedHistoryBlank) {
        return portSpeedHistoryBlankMapper.insert(portSpeedHistoryBlank);
    }


    @Override
    public int insertList(List<PortSpeedHistoryBlank> portSpeedHistoryBlanks) {
        if (portSpeedHistoryBlanks.size() == 0) {
            return 1;
        }
        return portSpeedHistoryBlankMapper.insertList(portSpeedHistoryBlanks);
    }

    @Override
    public int update(PortSpeedHistoryBlank portSpeedHistoryBlank) {
        return portSpeedHistoryBlankMapper.update(portSpeedHistoryBlank);
    }

    @Override
    public int updateList(List<PortSpeedHistoryBlank> portSpeedHistoryBlanks) {
        if (portSpeedHistoryBlanks.size() == 0) {
            return 1;
        }
        int ret = portSpeedHistoryBlankMapper.updateList(portSpeedHistoryBlanks);
        if (ret != portSpeedHistoryBlanks.size()) {
            ret = 0;
            for (PortSpeedHistoryBlank portSpeedHistoryBlank : portSpeedHistoryBlanks) {
                ret += portSpeedHistoryBlankMapper.update(portSpeedHistoryBlank);
            }
        }
        return ret;
    }

    @Override
    public List<PortSpeedHistoryBlank> selectByPort(String switchPort) {
        PortSpeedHistoryBlank portSpeedHistoryBlank = new PortSpeedHistoryBlank();
        portSpeedHistoryBlank.setSwitchPort(switchPort);
        return portSpeedHistoryBlankMapper.selectByPort(portSpeedHistoryBlank);
    }

    @Override
    public List<PortSpeedHistoryBlank> select() {
        return portSpeedHistoryBlankMapper.select();
    }

    @Override
    public int delete() {
        PortSpeedHistoryBlank portSpeedHistoryBlank = new PortSpeedHistoryBlank();
        portSpeedHistoryBlank.setTimeStart(DateUtil.beforeNowDate(portSpeedSaveTime));
        portSpeedHistoryBlank.setTimeEnd(DateUtil.beforeNowDate(portSpeedSaveTime));
        int ret = portSpeedHistoryBlankMapper.deleteHistory(portSpeedHistoryBlank);
        return ret;
    }
}
