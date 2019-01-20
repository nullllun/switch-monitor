package cn.albumenj.switchmonitor.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Albumen
 */
public class PermissionConst {
    public final static Integer ADMINISTRATOR = 1;
    public final static Integer STAFF = 2;
    public final static Integer VIEWER = 3;
    public final static Map<Integer, String> PERMISSION = new HashMap<Integer, String>() {
        {
            put(1, "ADMINISTRATOR");
            put(2, "STAFF");
            put(3, "VIEWER");
        }
    };
}
