package cn.albumenj.switchmonitor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwitchesStatusDto {
    private String ip;
    private Integer reachable;
    private Long downTime;
    private String upTime;
    private String model;
    private String name;
    private String cpuLoad;
    private String memUsed;
    private String temp;
    private String desc;
    private Long infoTime;

    public SwitchesStatusDto() {
        this.reachable = 0;
        this.downTime = -1L;
        this.upTime = "待获取";
        this.cpuLoad = "待获取";
        this.memUsed = "待获取";
        this.temp = "待获取";
        this.desc = "";
        this.infoTime = -1L;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getReachable() {
        return reachable;
    }

    public void setReachable(Integer reachable) {
        this.reachable = reachable;
    }

    public Long getDownTime() {
        return downTime;
    }

    public void setDownTime(Long downTime) {
        this.downTime = downTime;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(String cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public String getMemUsed() {
        return memUsed;
    }

    public void setMemUsed(String memUsed) {
        this.memUsed = memUsed;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getInfoTime() {
        return infoTime;
    }

    public void setInfoTime(Long infoTime) {
        this.infoTime = infoTime;
    }
}
