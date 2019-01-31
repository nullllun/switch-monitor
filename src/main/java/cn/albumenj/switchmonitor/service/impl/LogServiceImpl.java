package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.Log;
import cn.albumenj.switchmonitor.dao.LogMapper;
import cn.albumenj.switchmonitor.service.LogService;
import cn.albumenj.switchmonitor.service.WechatUserService;
import cn.albumenj.switchmonitor.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 日志
 *
 * @author albumen
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;
    @Autowired
    private WechatUserService wechatUserService;

    @Value("${history.log-saveTime}")
    Integer logSaveTime;

    @Override
    @Async
    public Integer insert(Log log) {
        if ("".equals(log.getOperator())) {
            log.setOperator(wechatUserService.selectName(log.getOpenId()));
        }
        return logMapper.insert(log);
    }

    @Override
    @Async
    public Integer insertList(List<Log> logs) {
        return logMapper.insertList(logs);
    }

    @Override
    public int deleteHistory() {
        Log log = new Log();
        log.setTimeStamp(DateUtil.beforeNowDate(logSaveTime).getTime());
        return logMapper.deleteHistory(log);
    }

    @Override
    public List<Log> selectByLevel(Log log) {
        return logMapper.selectByLevel(log);
    }

    @Override
    public List<Log> selectByType(Log log) {
        return logMapper.selectByType(log);
    }
}
