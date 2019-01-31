package cn.albumenj.switchmonitor.dto;

/**
 * 网页登录信息
 *
 * @author albumen
 */
public class WebLoginInfoDto {
    private String ip;
    private Long timeStamp;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
