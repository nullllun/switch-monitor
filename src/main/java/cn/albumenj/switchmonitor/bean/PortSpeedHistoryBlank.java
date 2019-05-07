package cn.albumenj.switchmonitor.bean;

import java.util.Date;

/**
 * 端口流量速率（历史，空）
 *
 * @author Albumen
 */
public class PortSpeedHistoryBlank {
    private Long id;
    private Integer switchId;
    private Integer portIndex;
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
