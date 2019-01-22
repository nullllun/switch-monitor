package cn.albumenj.switchmonitor.bean;

import java.util.Date;
import java.util.UUID;

/**
 * 端口状态（当前）
 *
 * @author Albumen
 */
public class PortStatus {
    private String id;
    private Integer switchId;
    private Integer portIndex;
    private String switchPort;
    private Integer portId;
    private Integer cvlan;
    private Integer pvlan;
    private String name;
    private Long inData;
    private Long outData;
    private String upTime;
    private String des;
    private Integer speed;
    private Integer status;
    private Date timeStamp;

    public PortStatus() {
        this.id = UUID.randomUUID().toString();
        this.switchId = -2;
        this.portIndex = -2;
        this.name="";
        this.portId = -1;
        this.inData = -2L;
        this.outData = -2L;
        this.upTime = "";
        this.des = "";
        this.speed = -2;
        this.status = -2;
        this.timeStamp = null;
    }

    public Integer getCvlan() {
        return cvlan;
    }

    public void setCvlan(Integer cvlan) {
        this.cvlan = cvlan;
    }

    public Integer getPvlan() {
        return pvlan;
    }

    public void setPvlan(Integer pvlan) {
        this.pvlan = pvlan;
    }

    public Integer getPortId() {
        return portId;
    }

    public void setPortId(Integer portId) {
        this.portId = portId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
