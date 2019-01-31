package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.PortSpeed;
import cn.albumenj.switchmonitor.dao.PortSpeedMapper;
import cn.albumenj.switchmonitor.service.PortSpeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Albumen
 */
@Service
public class PortSpeedServiceImpl implements PortSpeedService {
    private static List<PortSpeed> portSpeedRest = new LinkedList<>();

    @Autowired
    private PortSpeedMapper portSpeedMapper;

    @Override
    public int insert(PortSpeed portSpeed) {
        return portSpeedMapper.insert(portSpeed);
    }

    @Override
    public int insertList(List<PortSpeed> portSpeeds) {
        int ret = portSpeedMapper.insertList(portSpeeds);
        if (ret != portSpeeds.size()) {
            for (PortSpeed portSpeed : portSpeeds) {
                ret += portSpeedMapper.insert(portSpeed);
            }
        }
        return ret;
    }

    @Override
    public int update(PortSpeed portSpeed) {
        if (portSpeedMapper.update(portSpeed) == 0) {
            return portSpeedMapper.insert(portSpeed);
        } else {
            return 1;
        }
    }

    @Override
    public int updateList(List<PortSpeed> portSpeeds) {
        if (portSpeeds.size() == 0) {
            return 1;
        }
        int row = portSpeedMapper.updateList(portSpeeds);
        if (row != portSpeeds.size()) {
            for (PortSpeed portSpeed : portSpeeds) {
                row += update(portSpeed);
            }
        }
        return row;

    }

    @Override
    public List<PortSpeed> selectBySwitch(PortSpeed portSpeed) {
        return portSpeedMapper.selectBySwitch(portSpeed);
    }

    @Override
    public List<PortSpeed> select() {
        return portSpeedMapper.select();
    }
}
