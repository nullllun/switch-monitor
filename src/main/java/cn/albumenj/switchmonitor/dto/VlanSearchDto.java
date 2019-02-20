package cn.albumenj.switchmonitor.dto;

/**
 * Vlan查询
 *
 * @author Albumen
 */
public class VlanSearchDto {
    private String ip;
    private String portName;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }
}
