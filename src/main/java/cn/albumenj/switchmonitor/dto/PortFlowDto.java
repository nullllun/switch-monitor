package cn.albumenj.switchmonitor.dto;

import java.sql.Date;

public class PortFlowDto {
    private Long timestamp;
    private Integer in;
    private Integer out;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getIn() {
        return in;
    }

    public void setIn(Integer in) {
        this.in = in;
    }

    public Integer getOut() {
        return out;
    }

    public void setOut(Integer out) {
        this.out = out;
    }
}
