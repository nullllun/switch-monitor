package cn.albumenj.switchmonitor.bean;

/**
 * 权限用户表
 *
 * @author Albumen
 */
public class User {
    private String username;
    private String password;
    private String permission;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
