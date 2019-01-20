package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.User;

import java.util.List;

public interface UserService {

    int insert(User user);

    int insertList(List<User> users);

    int update(User user);

    User check(User user);
}
