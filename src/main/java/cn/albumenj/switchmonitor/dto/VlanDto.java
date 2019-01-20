package cn.albumenj.switchmonitor.dto;

public class VlanDto {
    private String id;
    private String ip;
    private Integer portId;
    private Integer cvlan;
    private Integer pvlan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPortId() {
        return portId;
    }

    public void setPortId(Integer portId) {
        this.portId = portId;
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
}
