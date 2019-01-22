package cn.albumenj.switchmonitor.dto;

import java.util.Date;

/**
 * 端口流量记录 数据库获取原始数据
 * 单条
 *
 * @author Albumen
 */
public class PortFlowOriginDto {
    private Date timestamp;
    private Integer in;
    private Integer out;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
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
