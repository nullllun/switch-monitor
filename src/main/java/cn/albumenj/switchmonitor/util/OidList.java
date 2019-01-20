package cn.albumenj.switchmonitor.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OidList {
    /**
     * 运行时间
     */
    private static final String UP_TIME = "1.3.6.1.2.1.1.3";
    /**
     * 接口名字 1.3.6.1.2.1.31.1.1.1.1
     */
    private static final String IF_NAME = "1.3.6.1.2.1.2.2.1.2";
    private static final String IF_INDEX = "1.3.6.1.2.1.2.2.1.1";
    /**
     * 接口状态 
     * up(1),down(2),testing(3),unknown(4),dormant(5),notPresent(6),lowerLayerDown(7)
     */
    private static final String IF_STATUS = "1.3.6.1.2.1.2.2.1.8";
    /**
     * 接口IP
     */
    private static final String IF_IP = "1.3.6.1.4.1.2011.5.25.41.1.2.1.1.1";
    /**
     * 接口索引
     */
    private static final String IF_IPINDEX = "1.3.6.1.4.1.2011.5.25.41.1.2.1.1.2";
    /**
     * 接口子网掩码
     */
    private static final String IF_IPMASK = "1.3.6.1.4.1.2011.5.25.41.1.2.1.1.3";
    /**
     * 该接口入方向通过的总字节数 1.3.6.1.2.1.2.2.1.10（32位） 1.3.6.1.2.1.31.1.1.1.6 (增强版，64位)
     */
    private static final String IF_IN = "1.3.6.1.2.1.31.1.1.1.6";
    /**
     * 该接口出方向通过的总字节数 1.3.6.1.2.1.2.2.1.16（32位） 1.3.6.1.2.1.31.1.1.1.10 (增强版，64位)
     */
    private static final String IF_OUT = "1.3.6.1.2.1.31.1.1.1.10";
    /**
     * 1.3.6.1.2.1.2.2.1.9.6 端口 uptime
     */
    private static final String IF_UPTIME = "1.3.6.1.2.1.2.2.1.9";
    /**
     * 接口描述
     */
    private static final String IF_DESCR = "1.3.6.1.2.1.31.1.1.1.18";
    /**
     * 接口带宽，单位为 Mbps
     */
    private static final String IF_SPEED = "1.3.6.1.2.1.31.1.1.1.15";
    /**
     * 设备名
     */
    private static final String NAME = "1.3.6.1.2.1.1.5";  

    private static final Map<String,String> CPU_LOAD;
    private static final Map<String,String> MEM_USED;
    private static final Map<String,String> TEMP;

    private String model;

    static {
        CPU_LOAD = new HashMap<>();
        MEM_USED = new HashMap<>();
        TEMP = new HashMap<>();

        CPU_LOAD.put("S2700","1.3.6.1.4.1.2011.5.25.31.1.1.1.1.5");
        CPU_LOAD.put("S5720","1.3.6.1.4.1.2011.5.25.31.1.1.1.1.5");
        CPU_LOAD.put("S3050","1.3.6.1.4.1.2011.5.25.31.1.1.1.1.5");
        CPU_LOAD.put("S12708","1.3.6.1.4.1.2011.5.25.31.1.1.1.1.5");
        CPU_LOAD.put("E152","1.3.6.1.4.1.25506.2.6.1.1.1.1.6");
        CPU_LOAD.put("E152B","1.3.6.1.4.1.25506.2.6.1.1.1.1.6");

        MEM_USED.put("S2700","1.3.6.1.4.1.2011.5.25.31.1.1.1.1.7");
        MEM_USED.put("S5720","1.3.6.1.4.1.4881.1.1.10.2.35.1.1.1.3.1");
        MEM_USED.put("S3050","1.3.6.1.4.1.4881.1.1.10.2.35.1.1.1.3.1");
        MEM_USED.put("S12708","1.3.6.1.4.1.4881.1.1.10.2.35.1.1.1.3.1");
        MEM_USED.put("E152","1.3.6.1.4.1.25506.2.6.1.1.1.1.8");
        MEM_USED.put("E152B","1.3.6.1.4.1.25506.2.6.1.1.1.1.8");

        TEMP.put("S2700","1.3.6.1.4.1.2011.5.25.31.1.1.1.1.11");
        TEMP.put("S5720","1.3.6.1.4.1.4881.1.1.10.2.1.1.16");
        TEMP.put("S3050","1.3.6.1.4.1.4881.1.1.10.2.1.1.16");
        TEMP.put("S12708","1.3.6.1.4.1.4881.1.1.10.2.1.1.16");
        TEMP.put("E152","1.3.6.1.4.1.25506.2.6.1.1.1.1.12");
        TEMP.put("E152B","1.3.6.1.4.1.25506.2.6.1.1.1.1.12");
    }

    public OidList(String model) {
        this.model = model;
    }

    public String getUpTime() {
        return UP_TIME;
    }

    public String getIfName() {
        return IF_NAME;
    }

    public String getIfIndex() {
        return IF_INDEX;
    }

    public String getIfStatus() {
        return IF_STATUS;
    }

    public String getIfIp() {
        return IF_IP;
    }

    public String getIfIpindex() {
        return IF_IPINDEX;
    }

    public String getIfIpmask() {
        return IF_IPMASK;
    }

    public String getIfIn() {
        return IF_IN;
    }

    public String getIfOut() {
        return IF_OUT;
    }

    public String getIfUptime() {
        return IF_UPTIME;
    }

    public String getIfDescr() {
        return IF_DESCR;
    }

    public String getIfSpeed() {
        return IF_SPEED;
    }

    public String getNAME() {
        return NAME;
    }

    public String getCpuLoad() {
        return CPU_LOAD.get(model);
    }

    public String getMemUsed() {
        return MEM_USED.get(model);
    }

    public String getTEMP() {
        return TEMP.get(model);
    }
}