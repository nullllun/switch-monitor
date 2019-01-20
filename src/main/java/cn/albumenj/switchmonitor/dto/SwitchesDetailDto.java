package cn.albumenj.switchmonitor.dto;

import cn.albumenj.switchmonitor.bean.SwitchesStatus;

import java.util.Date;
import java.util.List;

public class SwitchesDetailDto extends SwitchesStatus {
    private String model;
    private Integer reachable;
    private Date downTime;
    private String originIp;
    private List<SwitchesPortDetailDto> switchesPortDetailDto;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getReachable() {
        return reachable;
    }

    public void setReachable(Integer reachable) {
        this.reachable = reachable;
    }

    public Date getDownTime() {
        return downTime;
    }

    public void setDownTime(Date downTime) {
        this.downTime = downTime;
    }

    public String getOriginIp() {
        return originIp;
    }

    public void setOriginIp(String originIp) {
        this.originIp = originIp;
    }

    public List<SwitchesPortDetailDto> getSwitchesPortDetailDto() {
        return switchesPortDetailDto;
    }

    public void setSwitchesPortDetailDto(List<SwitchesPortDetailDto> switchesPortDetailDto) {
        this.switchesPortDetailDto = switchesPortDetailDto;
    }
}
