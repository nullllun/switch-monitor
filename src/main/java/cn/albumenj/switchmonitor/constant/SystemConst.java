package cn.albumenj.switchmonitor.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 分别操作系统
 *
 * @author Albumen
 */
@Component
public class SystemConst {
    @Value("${system.os}")
    Integer os;

    private final static Map<Integer, String> OS = new HashMap<>(2);

    static {
        OS.put(1, "Linux");
        OS.put(2, "Windows");
    }

    public boolean isLinux() {
        return "Linux".equals(OS.get(os));
    }
}
