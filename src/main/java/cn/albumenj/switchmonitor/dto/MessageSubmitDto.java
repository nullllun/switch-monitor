package cn.albumenj.switchmonitor.dto;

import java.util.Map;

/**
 * Wechat Message Submit
 *
 * @author albumen
 */
public class MessageSubmitDto {
    private String touser;
    private String msgtype;
    private String agentid;
    private Map<String, String> text;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public Map<String, String> getText() {
        return text;
    }

    public void setText(Map<String, String> text) {
        this.text = text;
    }
}
