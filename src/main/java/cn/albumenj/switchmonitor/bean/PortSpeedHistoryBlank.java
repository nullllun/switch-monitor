package cn.albumenj.switchmonitor.bean;

import java.util.Date;

public class PortSpeedHistoryBlank {
    private Long id;
    private String switchPort;
    private Date timeStart;
    private Date timeEnd;
    private Integer latest;

    public Integer getLatest() {
        return latest;
    }

    public void setLatest(Integer latest) {
        this.latest = latest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSwitchPort() {
        return switchPort;
    }

    public void setSwitchPort(String switchPort) {
        this.switchPort = switchPort;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }
}
