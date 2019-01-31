package cn.albumenj.switchmonitor.dto;

import javax.websocket.Session;

/**
 * 长连接Session状态维护
 *
 * @author albumen
 */
public class SessionDto {
    private Session session;
    private Long time;

    public SessionDto(Session session) {
        this.session = session;
        this.time = System.currentTimeMillis();
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
