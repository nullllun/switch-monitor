package cn.albumenj.switchmonitor.bean;

import java.util.Date;
import java.util.UUID;

public class PortStatusHistory {
    private String id;
    private Integer switchId;
    private Integer portIndex;
    private Long inData;
    private Long outData;
    private Date timeStamp;

    public PortStatusHistory() {
        this.id = UUID.randomUUID().toString();
        this.switchId = 0;
        this.portIndex = -2;
        this.inData = -2L;
        this.outData = -2L;
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

    public Integer getPortIndex() {
        return portIndex;
    }

    public void setPortIndex(Integer portIndex) {
        this.portIndex = portIndex;
    }

    public Long getInData() {
        return inData;
    }

    public void setInData(Long inData) {
        this.inData = inData;
    }

    public Long getOutData() {
        return outData;
    }

    public void setOutData(Long outData) {
        this.outData = outData;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
