package cn.albumenj.switchmonitor.schedule;

import cn.albumenj.switchmonitor.dto.WarningDto;
import cn.albumenj.switchmonitor.util.DateUtil;
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
    @Autowired
    SwitchesBriefFetch switchesBriefFetch;

    @Value("${threshold.reach}")
    Integer reachThreshold;

    private static ConcurrentHashMap<String, WarningDto> reachSend = new ConcurrentHashMap<>();

    public void deviceReachable() {
        List<String> message = new LinkedList<>();
        List<WarningDto> reach = switchesBriefFetch.getBriefStatusDtoReach().getWarning();
        HashMap<String, WarningDto> send = new HashMap<>();
        send.putAll(reachSend);

        if (reach != null && reach.size() > 0) {
            for (WarningDto warningDto : reach) {
                if (warningDto.getDownTime() < DateUtil.beforeNowMinute(reachThreshold).getTime()) {
                    if (send.get(warningDto.getIp()) == null) {
                        reachSend.put(warningDto.getIp(), warningDto);
                        String msg = "[监控消息]交换机炸了！\r\n" +
                                warningDto.getBuilding() + " " + warningDto.getIp() + "(" + warningDto.getModel() + ")";
                        message.add(msg);

                        System.out.println(msg);
                    } else {
                        send.remove(warningDto.getIp());
                    }
                }
            }
        }
        //TODO:顺序恢复
        if (send.size() > 0) {
            Set<Map.Entry<String, WarningDto>> entries = send.entrySet();
            for (Map.Entry<String, WarningDto> entry : entries) {
                reachSend.remove(entry.getKey());

                String time = DateUtil.getTime(System.currentTimeMillis() - entry.getValue().getDownTime());

                String msg = "[监控消息]交换机上线了！\r\n" +
                        entry.getValue().getBuilding() + " " + entry.getValue().getIp() + "(" + entry.getValue().getModel() + ")"
                        + "总掉线时间：" + time;
                message.add(msg);

                System.out.println(msg);
            }
        }
    }
}
