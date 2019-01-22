package cn.albumenj.switchmonitor.dto;

/**
 * 端口状态
 *
 * @author Albumen
 */
public class PortStatusDto {
    private String portIndex;
    private Long inData;
    private Long outData;
    private String des;
    private Long inSpeed;
    private Long outSpeed;
    private Integer speed;
    private String name;
    private String upTime;
    private String status;

    public String getPortIndex() {
        return portIndex;
    }

    public void setPortIndex(String portIndex) {
        this.portIndex = portIndex;
    }

    public Long getInData() {
        return inData;
    }

    public void setInData(Long inData) {
        this.inData = inData;
    }

    public Long getOutData() {
        return outData;
    }

    public void setOutData(Long outData) {
        this.outData = outData;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
