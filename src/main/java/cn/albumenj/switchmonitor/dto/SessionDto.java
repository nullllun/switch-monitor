package cn.albumenj.switchmonitor.dto;

import javax.websocket.Session;
import java.util.Date;

/**
 * 长连接Session状态维护
 *
 * @author albumen
 */
public class SessionDto {
    private Session session;
    private Date time;

    public SessionDto(Session session) {
        this.session = session;
        this.time = new Date();
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
