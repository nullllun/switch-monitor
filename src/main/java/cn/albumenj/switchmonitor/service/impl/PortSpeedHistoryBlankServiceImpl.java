package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.PortSpeedHistoryBlank;
import cn.albumenj.switchmonitor.dao.PortSpeedHistoryBlankMapper;
import cn.albumenj.switchmonitor.service.PortSpeedHistoryBlankService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PortSpeedHistoryBlankServiceImpl implements PortSpeedHistoryBlankService {

    @Resource
    private PortSpeedHistoryBlankMapper portSpeedHistoryBlankMapper;

    @Override
    public int insert(PortSpeedHistoryBlank portSpeedHistoryBlank) {
        return portSpeedHistoryBlankMapper.insert(portSpeedHistoryBlank);
    }


    @Override
    public int insertList(List<PortSpeedHistoryBlank> portSpeedHistoryBlanks) {
        return portSpeedHistoryBlankMapper.insertList(portSpeedHistoryBlanks);
    }

    @Override
    public int update(PortSpeedHistoryBlank portSpeedHistoryBlank) {
        return portSpeedHistoryBlankMapper.update(portSpeedHistoryBlank);
    }

    @Override
    public int updateList(List<PortSpeedHistoryBlank> portSpeedHistoryBlanks) {
        return portSpeedHistoryBlankMapper.updateList(portSpeedHistoryBlanks);
    }

    @Override
    public List<PortSpeedHistoryBlank> selectByPort(String switchPort) {
        PortSpeedHistoryBlank portSpeedHistoryBlank = new PortSpeedHistoryBlank();
        portSpeedHistoryBlank.setSwitchPort(switchPort);
        return portSpeedHistoryBlankMapper.selectByPort(portSpeedHistoryBlank);
    }

    @Override
    public List<PortSpeedHistoryBlank> selectOld() {
        return portSpeedHistoryBlankMapper.selectOld();
    }
}
