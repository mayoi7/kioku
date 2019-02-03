package com.akira.kioku.service.impl;

import com.akira.kioku.constant.UserConstant;
import com.akira.kioku.po.User;
import com.akira.kioku.repository.UserRepository;
import com.akira.kioku.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kripath
 * @date Created in 16:58 2019/2/1
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Integer findRoleByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            return null;
        }
        return user.getRole();
    }

    @Override
    public User registerUser(User user) {
        // 设置权限为普通用户
        user.setRole(UserConstant.USER_ROLE);
        return userRepository.save(user);
    }
}
