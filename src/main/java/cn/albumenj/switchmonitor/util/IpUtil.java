package cn.albumenj.switchmonitor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * IP地址工具类
 *
 * @author Albumen
 */
public class IpUtil {
    private final static Logger logger = LoggerFactory.getLogger(IpUtil.class);
    private final static String SUFFIX = " 网段";

    public static String getSegment(String ip, int reg) {
        String[] ips = ip.split("\\.");
        if (ip.endsWith(SUFFIX)) {
            return ip;
        } else if (ips.length != 4) {
            logger.warn("IP地址格式错误");
            return "";
        } else {
            StringBuilder stringBuilder = new StringBuilder(ips[0]);
            for (int i = 1; i < reg; i++) {
                stringBuilder.append(".");
                stringBuilder.append(ips[i]);
            }
            stringBuilder.append(SUFFIX);
            return stringBuilder.toString();
        }
    }

    public static boolean isSegment(String ip) {
        return ip.endsWith(SUFFIX);
    }

    /**
     * @param @param deviceIp
     * @return boolean true-能ping通，false-不能ping通
     * @throws
     * @Title: execPingCommand
     * @Description: 执行ping命令，查看设备是否可用 仅Linux平台
     */
    public static boolean execPingCommand(String deviceIp) {
        boolean networkUseable = false;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("ping -c 3 -W 1 " + deviceIp);
        } catch (IOException e1) {
            logger.error("System error: ", e1);
        }
        InputStreamReader r = new InputStreamReader(process.getInputStream());
        LineNumberReader returnData = new LineNumberReader(r);

        String returnMsg = "";
        String line = "";
        try {
            while ((line = returnData.readLine()) != null) {
                returnMsg += line;
            }

            if (returnMsg.contains("100% packet loss")) {
                networkUseable = false;
            } else {
                networkUseable = true;
            }
        } catch (IOException e) {
            logger.error("System error: ", e);
        } finally {
            try {
                returnData.close();

            } catch (IOException e) {
                logger.error("System error: ", e);
            }
            try {
                r.close();
            } catch (IOException e) {
                logger.error("System error: ", e);
            }
        }
        return networkUseable;
    }
}
