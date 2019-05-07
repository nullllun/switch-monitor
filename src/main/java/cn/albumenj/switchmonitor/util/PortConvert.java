package cn.albumenj.switchmonitor.util;

/**
 * 转换端口信息
 *
 * @author Albumen
 */
public class PortConvert {
    public static String complex(Integer switchId, Integer portIndex) {
        return "s" + switchId + "p" + portIndex;
    }

    public static Integer getSwitchId(String switchPort) {
        String[] data = switchPort.substring(1).split("p");
        return Integer.parseInt(data[0]);
    }

    public static Integer getPortIndex(String switchPort) {
        String[] data = switchPort.substring(1).split("p");
        return Integer.parseInt(data[1]);
    }
}
