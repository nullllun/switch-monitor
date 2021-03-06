package cn.albumenj.switchmonitor.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 权限
 *
 * @author Albumen
 */
public class PermissionConst {
    public final static Integer ADMINISTRATOR = 1;
    public final static Integer STAFF = 2;
    public final static Integer VIEWER = 3;
    public final static Map<Integer, String> PERMISSION = new HashMap<Integer, String>() {
        {
            put(1, "ROLE_ADMINISTRATOR");
            put(2, "ROLE_STAFF");
            put(3, "ROLE_VIEWER");
        }
    };
}
