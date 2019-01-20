package cn.albumenj.switchmonitor.dto;

import cn.albumenj.switchmonitor.constant.PageCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Albumen
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageCodeDto {
    private Integer code;
    private String msg;
    private Object object;

    public PageCodeDto() {
    }

    public PageCodeDto(PageCodeEnum pageCodeEnum) {
        this.code = pageCodeEnum.getCode();
        this.msg = pageCodeEnum.getMsg();
    }

    public PageCodeDto(PageCodeEnum pageCodeEnum, Object object) {
        this.code = pageCodeEnum.getCode();
        this.msg = pageCodeEnum.getMsg();
        this.object = object;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
