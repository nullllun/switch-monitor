package cn.albumenj.switchmonitor.util.qrcode;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * @author liuyueyi
 */
public class Base64Util {
    public static String encode(ByteArrayOutputStream outputStream) {
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }
}