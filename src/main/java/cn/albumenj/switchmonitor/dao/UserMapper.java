package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Albumen
 */
@Mapper
public interface UserMapper {
    /**
     * 插入单条记录
     *
     * @param user
     * @return
     */
    int insert(@Param("user") User user);

    /**
     * 通过用户名获取数据
     *
     * @param user
     * @return
     */
    User selectByUsername(@Param("user") User user);

    /**
     * 获取所有数据
     *
     * @return
     */
    List<User> select();

    /**
     * 插入多条数据
     *
     * @param users
     * @return
     */
    int insertList(@Param("users") List<User> users);

    /**
     * 修改单条数据
     *
     * @param user
     * @return
     */
    int update(@Param("user") User user);
}
