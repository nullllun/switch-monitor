package cn.albumenj.switchmonitor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 网页登录信息
 *
 * @author albumen
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebLoginInfoDto {
    private boolean contain;
    private Long timeStamp;

    public boolean isContain() {
        return contain;
    }

    public void setContain(boolean contain) {
        this.contain = contain;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
