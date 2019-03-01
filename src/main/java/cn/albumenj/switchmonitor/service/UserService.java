package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.User;

import java.util.List;

/**
 * 权限用户
 *
 * @author Albumen
 */
public interface UserService {

    /**
     * 插入单条数据
     *
     * @param user
     * @return
     */
    int insert(User user);

    /**
     * 插入多条数据
     *
     * @param users
     * @return
     */
    int insertList(List<User> users);

    /**
     * 修改单条数据
     *
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 验证登陆是否有效
     *
     * @param user
     * @return 验证成功返回 true ；验证失败返回 false
     */
    boolean check(User user);
}
