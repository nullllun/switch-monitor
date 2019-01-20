package cn.albumenj.switchmonitor.dto;

import java.util.Date;

public class SwitchesPortDetailDto {
    private Integer switchId;
    private String portIndex;
    private String name;
    private Long inSpeed;
    private Long outSpeed;
    private Integer speedMax;
    private Date speedTime;

    public Integer getSwitchId() {
        return switchId;
    }

    public void setSwitchId(Integer switchId) {
        this.switchId = switchId;
    }

    public String getPortIndex() {
        return portIndex;
    }

    public void setPortIndex(String portIndex) {
        this.portIndex = portIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getSpeedMax() {
        return speedMax;
    }

    public void setSpeedMax(Integer speedMax) {
        this.speedMax = speedMax;
    }

    public Date getSpeedTime() {
        return speedTime;
    }

    public void setSpeedTime(Date speedTime) {
        this.speedTime = speedTime;
    }
}
