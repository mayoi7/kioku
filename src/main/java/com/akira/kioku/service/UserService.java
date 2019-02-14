package com.akira.kioku.service;

import com.akira.kioku.dto.UserDetail;
import com.akira.kioku.po.User;

import java.util.List;

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

    /**
     * 注册用户
     * 用户权限默认置为11(普通用户)
     * @param user 将要注册的用户信息
     * @return 插入后的数据库中的用户记录（主要为了获取插入后的id）
     */
    User registerUser(User user);

    /**
     * 返回指定页号的用户详细信息数据列表
     * @param page 页码
     * @return {@link UserDetail}的集合
     */
    List<UserDetail> listAllDetailInPage(Integer page);

    /**
     * 统计用户人数
     * @return 所有注册用户总数
     */
    Long countUser();

    /**
     * 通过用户名查询某用户的详细信息
     * @param username 用户名
     * @return {@link UserDetail}对象
     */
    UserDetail queryUserDetailByUsername(String username);

    /**
     * 删除用户
     * @param username 要删除用户的用户名
     * @return true表示删除成功，false表示删除失败(数据库中没有该用户)
     */
    boolean deleteByUsername(String username);

    /**
     * 锁定用户
     * @param username 要锁定用户的用户名
     * @return true表示锁定成功，false表示锁定失败(数据库中没有该用户)
     */
    boolean lockByUsername(String username);

    /**
     * 重置用户(将权限恢复置默认)
     * @param username 要重置用户的用户名
     * @return false表示重置失败(数据库中没有该用户)
     */
    boolean resetByUsername(String username);
}
