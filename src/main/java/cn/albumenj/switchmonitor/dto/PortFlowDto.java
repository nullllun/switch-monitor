package cn.albumenj.switchmonitor.dto;

/**
 * 端口流量记录 传输至客户端
 * 单条
 *
 * @author Albumen
 */
public class PortFlowDto {
    private Long timestamp;
    private Long in;
    private Long out;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
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
