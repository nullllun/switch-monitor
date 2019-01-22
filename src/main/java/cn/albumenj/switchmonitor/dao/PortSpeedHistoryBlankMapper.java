package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.PortSpeedHistoryBlank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Albumen
 */
@Mapper
public interface PortSpeedHistoryBlankMapper {
    /**
     * 插入单个记录
     *
     * @param portSpeedHistoryBlank
     * @return
     */
    int insert(@Param("portSpeedHistoryBlank") PortSpeedHistoryBlank portSpeedHistoryBlank);

    /**
     * 修改单个记录
     *
     * @param portSpeedHistoryBlank
     * @return
     */
    int update(@Param("portSpeedHistoryBlank") PortSpeedHistoryBlank portSpeedHistoryBlank);

    /**
     * 插入多条记录
     *
     * @param portSpeedHistoryBlanks
     * @return
     */
    int insertList(@Param("portSpeedHistoryBlanks") List<PortSpeedHistoryBlank> portSpeedHistoryBlanks);

    /**
     * 修改多条记录
     *
     * @param portSpeedHistoryBlanks 不允许有空值
     * @return
     */
    int updateList(@Param("portSpeedHistoryBlanks") List<PortSpeedHistoryBlank> portSpeedHistoryBlanks);

    /**
     * 修改过期数据
     * 暂不用
     *
     * @param portSpeedHistoryBlanks 不允许有空值
     * @return
     */
    int updateHistory(@Param("portSpeedHistoryBlanks") List<PortSpeedHistoryBlank> portSpeedHistoryBlanks);

    /**
     * 删除过期数据
     *
     * @param portSpeedHistoryBlank 设置过期时间
     * @return
     */
    int deleteHistory(@Param("portSpeedHistoryBlank") PortSpeedHistoryBlank portSpeedHistoryBlank);

    /**
     * 获取特定端口数据
     *
     * @param portSpeedHistoryBlank
     * @return
     */
    List<PortSpeedHistoryBlank> selectByPort(@Param("portSpeedHistoryBlank") PortSpeedHistoryBlank portSpeedHistoryBlank);

    /**
     * 获取所有最新数据
     * 相对于间断点之前的
     *
     * @return
     */
    List<PortSpeedHistoryBlank> select();

    /**
     * 获取过期数据
     * 暂不用
     *
     * @param portSpeedHistoryBlank
     * @return
     */
    List<PortSpeedHistoryBlank> selectOld(@Param("portSpeedHistoryBlank") PortSpeedHistoryBlank portSpeedHistoryBlank);
}
