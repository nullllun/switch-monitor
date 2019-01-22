package cn.albumenj.switchmonitor.bean;

import java.util.Date;
import java.util.UUID;

/**
 * 端口流量速率（当前数据）
 *
 * @author Albumen
 */
public class PortSpeed {
    private String id;
    private Integer switchId;
    private Integer portIndex;
    private String switchPort;
    private Long inSpeed;
    private Long outSpeed;
    private Date timeStamp;

    public PortSpeed() {
        this.id = UUID.randomUUID().toString();
        this.switchId = 0;
        this.portIndex = -2;
        this.inSpeed = -2L;
        this.outSpeed = -2L;
        this.timeStamp = new Date();
    }

    public String getSwitchPort() {
        return switchPort;
    }

    public void setSwitchPort(String switchPort) {
        this.switchPort = switchPort;
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

    public Integer getPortIndex() {
        return portIndex;
    }

    public void setPortIndex(Integer portIndex) {
        this.portIndex = portIndex;
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
