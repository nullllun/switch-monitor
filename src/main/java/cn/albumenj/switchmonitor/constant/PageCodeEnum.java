package cn.albumenj.switchmonitor.constant;

/**
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
     * 添加成功
     */
    ADD_SUCCESS(1100, "添加成功"),
    /**
     * 添加失败
     */
    ADD_FAILED(1101, "添加失败"),
    /**
     * 修改成功
     */
    MODIFY_SUCCESS(1200, "修改成功"),
    /**
     * 修改失败
     */
    MODIFY_FAILED(1201, "修改失败"),
    /**
     * 获取成功
     */
    GET_SUCCESS(1300, "获取成功"),
    /**
     * 取失败
     */
    GET_FAILED(1301, "获取失败"),
    /**
     * 删除成功
     */
    DELETE_SUCCESS(1400, "删除成功"),
    /**
     * 删除失败
     */
    DELETE_FAILED(1401, "删除失败"),
    /**
     * 借出成功
     */
    RENT_SUCCESS(1500, "借出成功"),
    /**
     * 借出失败
     */
    RENT_FAILED(1501, "借出失败"),
    /**
     * 归还成功
     */
    BACK_SUCCESS(1600, "归还成功"),
    /**
     * 归还失败
     */
    BACK_FAILED(1601, "归还失败"),
    /**
     * 登出成功
     */
    LOGOUT_SUCCESS(1700, "登出成功"),
    /**
     * 登出失败
     */
    LOGOUT_FAILED(1701, "登出失败"),
    /**
     * 认证失败
     */
    NOT_LOGIN(2000, "认证失败"),
    /**
     * 权限不足
     */
    PERMISSION_DENIED(2001, "权限不足"),
    /**
     * 系统错误
     */
    SYSTEM_ERROR(2002, "系统错误"),
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
