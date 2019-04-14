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
    private Long in;
    private Long out;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getIn() {
        return in;
    }

    public void setIn(Long in) {
        this.in = in;
    }

    public Long getOut() {
        return out;
    }

    public void setOut(Long out) {
        this.out = out;
    }
}
