package cn.albumenj.switchmonitor.schedule;

import cn.albumenj.switchmonitor.dto.WarningDto;
import cn.albumenj.switchmonitor.util.DateUtil;
import cn.albumenj.switchmonitor.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 微信公众号推送服务
 *
 * @author Albumen
 */
@Component
public class WechatPush {
    private final static Logger logger = LoggerFactory.getLogger(WechatPush.class);
    @Autowired
    SwitchesBriefFetch switchesBriefFetch;

    @Value("${threshold.reach}")
    Integer reachThreshold;

    private static ConcurrentHashMap<String, WarningDto> reachSend = new ConcurrentHashMap<>(16);
    private static ConcurrentHashMap<String, ExpireRecovery> recoveryMessage = new ConcurrentHashMap<>(16);
    List<String> push = new LinkedList<>();

    public void deviceReachable() {
        List<WarningDto> reach = switchesBriefFetch.getBriefStatusDtoReach().getWarning();
        HashMap<String, WarningDto> send = new HashMap<>(reachSend.size());
        send.putAll(reachSend);

        if (reach != null && reach.size() > 0) {
            for (WarningDto warningDto : reach) {
                if (warningDto.getDownTime() < DateUtil.beforeNowMinute(reachThreshold).getTime()) {
                    if (send.get(warningDto.getIp()) == null && !recoveryMessage.containsKey(IpUtil.getSegment(warningDto.getIp(),3))) {
                        reachSend.put(warningDto.getIp(), warningDto);
                        String msg = "[监控消息]交换机炸了！\r\n" +
                                warningDto.getBuilding() + " " + warningDto.getIp() + "(" + warningDto.getModel() + ")";
                        push.add(msg);

                        logger.info(msg);
                    } else {
                        send.remove(warningDto.getIp());
                    }
                }
            }
        }
        if (send.size() > 0) {
            Set<Map.Entry<String, WarningDto>> entries = send.entrySet();
            for (Map.Entry<String, WarningDto> entry : entries) {
                reachSend.remove(entry.getKey());
                recoveryMessage.put(entry.getKey(),new ExpireRecovery(new Date(),entry.getValue()));
            }
        }
        if(recoveryMessage.size()>0){
            // 信息沉淀
            Set<Map.Entry<String, ExpireRecovery>> entries = recoveryMessage.entrySet();
            for (Map.Entry<String, ExpireRecovery> entry : entries) {
                // 非网段IP或沉淀时间已过
                if(!IpUtil.isSegment(entry.getKey()) || entry.getValue().getTime().getTime() < DateUtil.beforeNowMinute(2).getTime()) {
                    recoveryMessage.remove(entry.getKey());

                    String time = DateUtil.getTime(System.currentTimeMillis() -
                            entry.getValue().getWarningDto().getDownTime());

                    String msg = "[监控消息]交换机上线了！\r\n" +
                            entry.getValue().getWarningDto().getBuilding() + " " +
                            entry.getValue().getWarningDto().getIp() + "(" +
                            entry.getValue().getWarningDto().getModel() + ")"
                            + "总掉线时间：" + time;
                    push.add(msg);

                    logger.info(msg);
                }
            }
        }
    }

    private class ExpireRecovery{
        private Date time;
        private WarningDto warningDto;

        public ExpireRecovery(Date time, WarningDto warningDto) {
            this.time = time;
            this.warningDto = warningDto;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        public WarningDto getWarningDto() {
            return warningDto;
        }

        public void setWarningDto(WarningDto warningDto) {
            this.warningDto = warningDto;
        }
    }
}
