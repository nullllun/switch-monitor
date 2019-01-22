package cn.albumenj.switchmonitor.bean;

import java.util.Date;
import java.util.UUID;

/**
 * 交换机状态（SNMP获取结果）
 *
 * @author Albumen
 */
public class SwitchesStatus {
    private String id;
    private Integer switchId;
    private String upTime;
    private String ip;
    private Integer ipIndex;
    private String ipMask;
    private String name;
    private Integer cpuLoad;
    private Integer memoryUsed;
    private Integer temp;
    private Date timeStamp;

    public SwitchesStatus() {
        this.id = UUID.randomUUID().toString();
        this.upTime = "";
        this.ip = "";
        this.ipIndex = -2;
        this.ipMask = "";
        this.name = "";
        this.cpuLoad = -2;
        this.memoryUsed = -2;
        this.temp = -2;
        this.timeStamp = null;
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

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getIpIndex() {
        return ipIndex;
    }

    public void setIpIndex(Integer ipIndex) {
        this.ipIndex = ipIndex;
    }

    public String getIpMask() {
        return ipMask;
    }

    public void setIpMask(String ipMask) {
        this.ipMask = ipMask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
