package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 日志
 *
 * @author albumen
 */
@Mapper
public interface LogMapper {
    /**
     * 插入单条数据
     *
     * @param log
     * @return
     */
    int insert(@Param("log") Log log);

    /**
     * 删除过期数据
     *
     * @param log
     * @return
     */
    int deleteHistory(@Param("log") Log log);

    /**
     * 插入多条数据
     *
     * @param logs
     * @return
     */
    int insertList(@Param("logs") List<Log> logs);

    /**
     * 获取数据
     *
     * @param log 以级别为筛选
     * @return
     */
    List<Log> selectByLevel(@Param("log") Log log);

    /**
     * 获取数据
     *
     * @param log 以类型为筛选
     * @return
     */
    List<Log> selectByType(@Param("log") Log log);
}
