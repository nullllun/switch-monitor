package cn.albumenj.switchmonitor.bean;

/**
 * 交换机基本数据（数据库需预先填好）
 *
 * @author Albumen
 */
public class SwitchesList {
    private Integer id;
    private String ip;
    private String model;
    private String building;
    private String description;
    private String readKey;
    private String writeKey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReadKey() {
        return readKey;
    }

    public void setReadKey(String readKey) {
        this.readKey = readKey;
    }

    public String getWriteKey() {
        return writeKey;
    }

    public void setWriteKey(String writeKey) {
        this.writeKey = writeKey;
    }
}
