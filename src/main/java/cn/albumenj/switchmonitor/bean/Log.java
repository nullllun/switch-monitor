package cn.albumenj.switchmonitor.bean;

/**
 * 日志记录
 *
 * @author albumen
 */
public class Log {
    private Long id;
    private Integer level;
    private Integer type;
    private String openId;
    private String operator;
    private String operation;
    private Long timeStamp;
    private String ip;

    public Log() {
        this.level = -2;
        this.type = -2;
        this.openId = "";
        this.operator = "";
        this.operation = "";
        this.timeStamp = System.currentTimeMillis();
        this.ip = "";
    }

    public Log(Integer level, Integer type, String openId, String operation ,String ip) {
        this.level = level;
        this.type = type;
        this.openId = openId;
        this.operator = "";
        this.operation = operation;
        this.timeStamp = System.currentTimeMillis();
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
