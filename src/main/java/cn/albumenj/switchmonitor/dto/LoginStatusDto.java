package cn.albumenj.switchmonitor.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 登陆结果
 *
 * @author Albumen
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginStatusDto {
    boolean success;
    String token;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
