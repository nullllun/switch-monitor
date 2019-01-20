package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    int insert(@Param("user") User user);

    User selectByUsername(@Param("user") User user);

    List<User> select();

    int insertList(@Param("users") List<User> users);

    int update(@Param("user") User user);
}
