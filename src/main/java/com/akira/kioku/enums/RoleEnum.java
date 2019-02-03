package com.akira.kioku.enums;

import com.akira.kioku.constant.UserConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Kripath
 * @date Created in 9:57 2019/2/2
 */
@Getter
@AllArgsConstructor
public enum RoleEnum implements CodeEnum {

    /**
     * 被锁定的用户，不能够进行日记的添加操作，仅拥有查看自己日记的权限
     */
    LOCKED_USER(UserConstant.LOCKED_ROLE, "locked"),

    /**
     * 普通用户
     */
    NORMAL_USER(UserConstant.USER_ROLE, "user"),

    /**
     * 普通管理员，拥有查看和锁定用户的权利
     */
    NORMAL_ADMIN(UserConstant.ADMIN_ROLE, "admin"),


    /**
     * 超级管理员，拥有包括赋予其他人管理员在内的所有权限
     */
    SUPER_ADMIN(UserConstant.SUPER_ADMIN_ROLE, "sp_admin"),
    ;

    private final Integer code;
    private final String msg;
}
