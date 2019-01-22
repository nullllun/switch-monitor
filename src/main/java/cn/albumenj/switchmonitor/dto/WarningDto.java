package cn.albumenj.switchmonitor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 报警时信息
 *
 * @author Albumen
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WarningDto {
    private String warning;
    private String ip;
    private String model;
    private Long downTime;
    private String cpuLoad;
    private String memUsed;
    private String temp;
    private String portName;
    private String portSpeed;

    public WarningDto() {
    }

    public WarningDto(String warning, String ip, String model) {
        this.warning = warning;
        this.ip = ip;
        this.model = model;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getDownTime() {
        return downTime;
    }

    public void setDownTime(Long downTime) {
        this.downTime = downTime;
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

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getPortSpeed() {
        return portSpeed;
    }

    public void setPortSpeed(String portSpeed) {
        this.portSpeed = portSpeed;
    }
}
