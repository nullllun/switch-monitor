package cn.albumenj.switchmonitor.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.UUID;

@Document(collection = "port_speed_history")
public class PortSpeedHistoryMongo {
    @Field("s")
    private Integer switchId;
    @Field("p")
    private Integer portIndex;
    @Field("i")
    private Long inSpeed;
    @Field("o")
    private Long outSpeed;
    @Field("t")
    private Date timeStamp;

    public PortSpeedHistoryMongo() {
        this.switchId = -2;
        this.portIndex = -2;
        this.inSpeed = -2L;
        this.outSpeed = -2L;
        this.timeStamp = new Date();
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

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
