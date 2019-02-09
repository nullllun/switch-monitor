package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.Log;

import java.util.List;

/**
 * 日志
 *
 * @author albumen
 */
public interface LogService {
    /**
     * 插入单条数据
     *
     * @param log
     * @return
     */
    Integer insert(Log log);

    /**
     * 删除过期数据
     *
     * @param
     * @return
     */
    int deleteHistory();

    /**
     * 插入多条数据
     * 插入失败转单条插入
     *
     * @param logs
     * @return
     */
    Integer insertList(List<Log> logs);

    /**
     * 获取数据
     *
     * @param level 以级别为筛选
     * @return
     */
    List<Log> selectByLevel(Integer level);

    /**
     * 获取数据
     *
     * @param type 以类型为筛选
     * @return
     */
    List<Log> selectByType(Integer type);

    /**
     * 获取数据（所有）
     *
     * @return 已处理逆序
     */
    List<Log> selectAll();
}
