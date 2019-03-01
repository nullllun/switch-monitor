package cn.albumenj.switchmonitor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;

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
        int length = 4;
        if (ip.endsWith(SUFFIX)) {
            return ip;
        } else if (ips.length != length) {
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
            process = Runtime.getRuntime().exec("ping -c 5 -W 1 " + deviceIp);
        } catch (IOException e1) {
            logger.error("System error: ", e1);
        }
        InputStreamReader r = new InputStreamReader(process.getInputStream());
        LineNumberReader returnData = new LineNumberReader(r);

        String returnMsg = "";
        String line = "";
        String flagLinux = "100% packet loss";
        try {
            while ((line = returnData.readLine()) != null) {
                returnMsg += line;
            }

            if (returnMsg.contains(flagLinux)) {
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


    /**
     * @Description: 获取客户端IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String unknown = "unknown";
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            String localhost = "127.0.0.1";
            if (ip.equals(localhost)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        int length = 15;
        if (ip != null && ip.length() > length) {
            String separator = ",";
            if (ip.indexOf(separator) > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
