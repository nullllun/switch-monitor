package cn.albumenj.switchmonitor.constant;

/**
 * 状态码
 *
 * TODO：优化状态码
 * @author Albumen
 */
@SuppressWarnings("ALL")
public enum PageCodeEnum {
    /**
     * 登陆成功
     */
    LOGIN_SUCCESS(1000, "登陆成功"),
    /**
     * 登陆失败
     */
    LOGIN_FAILED(1001, "登陆失败"),
    /**
     * 认证失败
     */
    NOT_LOGIN(2000, "认证失败"),
    /**
     * 权限足够
     */
    PERMISSION_ACCEPT(2001, "权限足够"),
    /**
     * 权限不足
     */
    PERMISSION_DENIED(2002, "权限不足"),
    /**
     * 系统错误
     */
    SYSTEM_ERROR(2003, "系统错误"),
    ;

    private Integer code;
    private String msg;

    public static final String KEY = "pageCode";

    PageCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
