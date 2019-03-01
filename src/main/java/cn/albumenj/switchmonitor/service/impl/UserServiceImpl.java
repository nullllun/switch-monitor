package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.User;
import cn.albumenj.switchmonitor.dao.UserMapper;
import cn.albumenj.switchmonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Albumen
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int insertList(List<User> users) {
        return userMapper.insertList(users);
    }

    @Override
    public int update(User user) {
        if (userMapper.update(user) != 1) {
            return userMapper.insert(user);
        } else {
            return 1;
        }
    }

    @Override
    public boolean check(User user) {
        User userDatabase = userMapper.selectByUsername(user);
        if (userDatabase != null) {
            if (userDatabase.getPassword().compareTo(user.getPassword()) == 0) {
                return true;
            }
        }
        return false;
    }
}
