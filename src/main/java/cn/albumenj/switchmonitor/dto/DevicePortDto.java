package cn.albumenj.switchmonitor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DevicePortDto {
    private List<String> ifDescr;
    private List<String> ifIn;
    private List<Integer> ifInSpeed;
    private List<String> ifIp;
    private List<String> ifName;
    private List<String> ifOut;
    private List<Integer> ifOutSpeed;
    private List<String> ifSpeed;
    private List<String> ifStatus;
    private List<String> ifUptime;

    public List<String> getIfDescr() {
        return ifDescr;
    }

    public void setIfDescr(List<String> ifDescr) {
        this.ifDescr = ifDescr;
    }

    public List<String> getIfIn() {
        return ifIn;
    }

    public void setIfIn(List<String> ifIn) {
        this.ifIn = ifIn;
    }

    public List<Integer> getIfInSpeed() {
        return ifInSpeed;
    }

    public void setIfInSpeed(List<Integer> ifInSpeed) {
        this.ifInSpeed = ifInSpeed;
    }

    public List<String> getIfIp() {
        return ifIp;
    }

    public void setIfIp(List<String> ifIp) {
        this.ifIp = ifIp;
    }

    public List<String> getIfName() {
        return ifName;
    }

    public void setIfName(List<String> ifName) {
        this.ifName = ifName;
    }

    public List<String> getIfOut() {
        return ifOut;
    }

    public void setIfOut(List<String> ifOut) {
        this.ifOut = ifOut;
    }

    public List<Integer> getIfOutSpeed() {
        return ifOutSpeed;
    }

    public void setIfOutSpeed(List<Integer> ifOutSpeed) {
        this.ifOutSpeed = ifOutSpeed;
    }

    public List<String> getIfSpeed() {
        return ifSpeed;
    }

    public void setIfSpeed(List<String> ifSpeed) {
        this.ifSpeed = ifSpeed;
    }

    public List<String> getIfStatus() {
        return ifStatus;
    }

    public void setIfStatus(List<String> ifStatus) {
        this.ifStatus = ifStatus;
    }

    public List<String> getIfUptime() {
        return ifUptime;
    }

    public void setIfUptime(List<String> ifUptime) {
        this.ifUptime = ifUptime;
    }
}
