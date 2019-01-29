package cn.albumenj.switchmonitor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IP地址工具类
 *
 * @author Albumen
 */
public class IpUtil {
    private final static Logger logger = LoggerFactory.getLogger(IpUtil.class);
    public static String getSegment(String ip,int reg){
        String[] ips = ip.split("\\.");
        String suffix = " 网段";
        if(ip.endsWith(suffix)){
            return ip;
        }
        else if(ips.length!=4){
            logger.warn("IP地址格式错误");
            return "";
        }
        else{
            StringBuilder stringBuilder = new StringBuilder(ips[0]);
            for(int i= 1;i<reg;i++){
                stringBuilder.append(".");
                stringBuilder.append(ips[i]);
            }
            stringBuilder.append(suffix);
            return stringBuilder.toString();
        }
    }
}
