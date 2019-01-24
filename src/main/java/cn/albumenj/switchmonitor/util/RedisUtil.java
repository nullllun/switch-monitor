package cn.albumenj.switchmonitor.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具
 *
 * @author Albumen
 */
@Component
public class RedisUtil {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Value("${security.jwtDefaultExp}")
    Integer expTime;

    /**
     * String类型缓存获取
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * String类型缓存保存
     *
     * @param key   键
     * @param value 值
     * @return true：成功；false：失败
     */
    public boolean set(String key, String value) throws Exception {
        return set(key, value, expTime);
    }

    /**
     * String类型缓存保存
     *
     * @param key   键
     * @param value 值
     * @return true：成功；false：失败
     */
    public boolean set(String key, String value, Integer expTime) throws Exception {
        if (StringUtils.isNotEmpty(key) && null != value) {
            redisTemplate.opsForValue().set(key, value, expTime, TimeUnit.SECONDS);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除键值
     *
     * @param key
     * @return
     */
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}

