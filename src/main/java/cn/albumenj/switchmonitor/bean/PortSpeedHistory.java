package cn.albumenj.switchmonitor.bean;

import java.util.Date;
import java.util.UUID;

public class PortSpeedHistory {
    private String id;
    private String switchPort;
    private Long inSpeed;
    private Long outSpeed;
    private Date timeStamp;

    public PortSpeedHistory() {
        this.id = UUID.randomUUID().toString();
        this.inSpeed = -2L;
        this.outSpeed = -2L;
        this.timeStamp = new Date();
        this.switchPort = "";
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
