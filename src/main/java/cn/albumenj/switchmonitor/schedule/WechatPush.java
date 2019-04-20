package cn.albumenj.switchmonitor.schedule;

import cn.albumenj.switchmonitor.dto.WarningDto;
import cn.albumenj.switchmonitor.util.DateUtil;
import cn.albumenj.switchmonitor.util.IpUtil;
import cn.albumenj.switchmonitor.util.WechatServer;
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
    @Autowired
    WechatServer wechatServer;

    @Value("${threshold.reach}")
    Integer reachThreshold;
    @Value("${threshold.pushRecovery}")
    Integer pushRecoveryTime;

    private static ConcurrentHashMap<String, WarningDto> reachSend = new ConcurrentHashMap<>(16);
    private static ConcurrentHashMap<String, ExpireRecovery> recoveryMessage = new ConcurrentHashMap<>(16);
    List<String> pushBroke = new LinkedList<>();
    List<String> pushRecovery = new LinkedList<>();

    public void deviceReachable() {
        pushBroke.clear();
        pushRecovery.clear();

        manageReachable();

        if (pushRecovery.size() > 0) {
            pushRecovery.add("当前还有 " + reachSend.size() + "台交换机 / 网段仍未恢复");
        }

        push(pushBroke, "[监控消息]交换机炸了！");
        push(pushRecovery, "[监控消息]交换机上线了！");

    }

    private void push(List<String> stringList, String head) {
        String newLine = "\r\n";
        StringBuilder stringBuilder = new StringBuilder();
        if (stringList.size() > 0) {
            stringBuilder.append(head + newLine);
            for (String str : stringList) {
                stringBuilder.append(newLine);
                stringBuilder.append(str);
                if (stringBuilder.length() > 400) {
                    wechatServer.sendWarnMessage(stringBuilder.toString());
                    System.out.println(stringBuilder.toString());
                    stringBuilder.setLength(0);
                    stringBuilder.append(head + newLine);
                }
            }
            if (stringBuilder.length() > 0 && stringBuilder.compareTo(new StringBuilder(head + newLine)) != 0) {
                wechatServer.sendWarnMessage(stringBuilder.toString());
                System.out.println(stringBuilder.toString());
            }
        }
    }

    public void manageReachable() {
        List<WarningDto> reach = switchesBriefFetch.getBriefStatusDtoReach().getWarning();
        HashMap<String, WarningDto> send = new HashMap<>(reachSend.size());
        send.putAll(reachSend);

        if (reach != null && reach.size() > 0) {
            for (WarningDto warningDto : reach) {
                if (warningDto.getDownTime() < DateUtil.beforeNowMinute(reachThreshold).getTime()) {
                    String prefix = IpUtil.getSegment(warningDto.getIp(), 3);
                    boolean flag = send.get(warningDto.getIp()) == null &&
                            !(recoveryMessage.containsKey(prefix) || reachSend.containsKey(prefix));
                    if (flag) {
                        reachSend.put(warningDto.getIp(), warningDto);

                        String time = DateUtil.getTime(System.currentTimeMillis() - warningDto.getDownTime());

                        String msg = warningDto.getBuilding()
                                + " " + warningDto.getIp() + " (" + warningDto.getModel() + ")" +
                                "\r\n 下线时间：" + time;
                        pushBroke.add(msg);

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
                recoveryMessage.put(entry.getKey(), new ExpireRecovery(new Date(), entry.getValue()));
            }
        }
        if (recoveryMessage.size() > 0) {
            // 信息沉淀
            Set<Map.Entry<String, ExpireRecovery>> entries = recoveryMessage.entrySet();
            for (Map.Entry<String, ExpireRecovery> entry : entries) {
                // 非网段IP或沉淀时间已过
                if (!IpUtil.isSegment(entry.getKey()) ||
                        entry.getValue().getTime().getTime() < DateUtil.beforeNowMinute(pushRecoveryTime).getTime()) {
                    recoveryMessage.remove(entry.getKey());

                    String time = DateUtil.getTime(System.currentTimeMillis() -
                            entry.getValue().getWarningDto().getDownTime());

                    String msg = entry.getValue().getWarningDto().getBuilding() + " " +
                            entry.getValue().getWarningDto().getIp() + " (" +
                            entry.getValue().getWarningDto().getModel() + ")"
                            + "\r\n总掉线时间：" + time + "\r\n";
                    pushRecovery.add(msg);

                    logger.info(msg);
                }
            }
        }
    }

    private class ExpireRecovery {
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
