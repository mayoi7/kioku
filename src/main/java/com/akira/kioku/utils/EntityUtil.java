package com.akira.kioku.utils;

import com.akira.kioku.po.User;

/**
 * 负责对pojo进行一些处理
 * @author Kripath
 * @date Created in 11:56 2019/2/3
 */
public class EntityUtil {

    /**
     * 将用户信息封装成用户对象，同时对密码进行加密
     * @return 封装后的用户对象
     */
    public static User encryptAndStorageAsUser(String username, String password,
                                               String email, String code) {
        // 对密码进行加密，用户名做盐值
        password = TokenUtil.makeTEncryptTokenBySaltedMd5(password, username).toString();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setCode(code);

        return user;
    }
}
