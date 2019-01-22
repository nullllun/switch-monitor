package cn.albumenj.switchmonitor.bean;

import java.util.Date;
import java.util.UUID;

/**
 * 交换机在线状态（历史）
 * 暂不用
 *
 * @author Albumen
 */
public class SwitchesReachableHistory {
    private String id;
    private Integer switchId;
    private Integer reachable;
    private Date downTime;
    private Date timeStamp;

    public SwitchesReachableHistory() {
        this.id = UUID.randomUUID().toString();
        this.switchId = -2;
        this.reachable = -2;
        this.downTime = new Date(-1);
        this.timeStamp = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSwitchId() {
        return switchId;
    }

    public void setSwitchId(Integer switchId) {
        this.switchId = switchId;
    }

    public Integer getReachable() {
        return reachable;
    }

    public void setReachable(Integer reachable) {
        this.reachable = reachable;
    }

    public Date getDownTime() {
        return downTime;
    }

    public void setDownTime(Date downTime) {
        this.downTime = downTime;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
