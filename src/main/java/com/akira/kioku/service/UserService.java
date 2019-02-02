package com.akira.kioku.service;

import com.akira.kioku.po.User;

/**
 * @author Kripath
 * @date Created in 16:58 2019/2/1
 */
public interface UserService {

    /**
     * 通过用户名查找用户
     * @param username 用户名
     * @return 查询的用户对象
     */
    User findByUsername(String username);

    /**
     * 通过用户名返回用户权限
     * @param username 用户名
     * @return 10：普通用户，11：管理员，12：被锁定的用户
     */
    Integer findRoleByUsername(String username);
}
