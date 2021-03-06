package cn.albumenj.switchmonitor.bean;

import java.util.Date;
import java.util.UUID;

/**
 * 端口流量速率（历史，非空）
 *
 * @author Albumen
 */
public class PortSpeedHistory {
    private String id;
    private Integer switchId;
    private Integer portIndex;
    private Long inSpeed;
    private Long outSpeed;
    private Date timeStamp;

    public PortSpeedHistory() {
        this.id = UUID.randomUUID().toString();
        this.inSpeed = -2L;
        this.outSpeed = -2L;
        this.timeStamp = new Date();
        this.switchId = -2;
        this.portIndex = -2;
    }

    public Integer getSwitchId() {
        return switchId;
    }

    public void setSwitchId(Integer switchId) {
        this.switchId = switchId;
    }

    public Integer getPortIndex() {
        return portIndex;
    }

    public void setPortIndex(Integer portIndex) {
        this.portIndex = portIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getInSpeed() {
        return inSpeed;
    }

    public void setInSpeed(Long inSpeed) {
        this.inSpeed = inSpeed;
    }

    public Long getOutSpeed() {
        return outSpeed;
    }

    public void setOutSpeed(Long outSpeed) {
        this.outSpeed = outSpeed;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
