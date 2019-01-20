package cn.albumenj.switchmonitor.bean;

import java.util.Date;
import java.util.UUID;

public class SwitchesStatusHistory {
    private String id;
    private Integer switchId;
    private Integer cpuLoad;
    private Integer memoryUsed;
    private Integer temp;
    private Date timeStamp;

    public SwitchesStatusHistory() {
        this.id = UUID.randomUUID().toString();
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

    public Integer getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(Integer cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public Integer getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(Integer memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
