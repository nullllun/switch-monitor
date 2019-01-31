package cn.albumenj.switchmonitor.util.QrCode;

import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageConfig;

import java.util.Map;

/**
 * @author liuyueyi
 */
public class QrCodeOptions {
    /**
     * 塞入二维码的信息
     */
    private String msg;


    /**
     * 二维码中间的logo
     */
    private String logo;


    /**
     * logo的样式， 目前支持圆角+普通
     */
    private LogoStyle logoStyle;


    /**
     * 生成二维码的宽
     */
    private Integer w;


    /**
     * 生成二维码的高
     */
    private Integer h;


    /**
     * 生成二维码的颜色
     */
    private MatrixToImageConfig matrixToImageConfig;


    private Map<EncodeHintType, Object> hints;


    /**
     * 生成二维码图片的格式 png, jpg
     */
    private String picType;


    public enum LogoStyle {
        ROUND,
        NORMAL;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public LogoStyle getLogoStyle() {
        return logoStyle;
    }

    public void setLogoStyle(LogoStyle logoStyle) {
        this.logoStyle = logoStyle;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public MatrixToImageConfig getMatrixToImageConfig() {
        return matrixToImageConfig;
    }

    public void setMatrixToImageConfig(MatrixToImageConfig matrixToImageConfig) {
        this.matrixToImageConfig = matrixToImageConfig;
    }

    public Map<EncodeHintType, Object> getHints() {
        return hints;
    }

    public void setHints(Map<EncodeHintType, Object> hints) {
        this.hints = hints;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }
}
