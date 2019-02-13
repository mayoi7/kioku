package com.akira.kioku.shiro;


import com.akira.kioku.enums.RoleEnum;
import com.akira.kioku.po.User;
import com.akira.kioku.service.UserService;
import com.akira.kioku.utils.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.EmitUtils;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 自定义shiro的验证规则
 * @author 刘昊楠
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {
    private UserService userService;

    @Autowired
    private void setUserMapper(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     *
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 前台输入的用户名和密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 从数据库获取对应用户名密码的用户
        User user = userService.findByUsername(token.getUsername());
        if(user == null) {throw new UnknownAccountException();}

        String password = user.getPassword();
//        if (!password.equals(new String((char[]) token.getCredentials()))) {
//            throw new IncorrectCredentialsException();
//        }
        //盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUsername());

        return new SimpleAuthenticationInfo(token.getPrincipal(), password, credentialsSalt,
                                            getName());
    }

    /**
     * 获取授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        //获得该用户角色
        Integer role = userService.findRoleByUsername(username);
        Set<String> roles = new HashSet<>();

        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        RoleEnum roleEnum = EnumUtil.getByCode(role, RoleEnum.class);

        // 该用户的权限码错误
        if(roleEnum == null) {
            log.error("[权限]用户{}在数据库中的权限数据异常，权限码为{}", username, role);
        } else {
            roles.add(roleEnum.getMsg());
            // 普通用户的权限码
            Integer normalUserCode = 11;

            // 赋予该用户所有低级权限
            for (int i = normalUserCode; i < role; i++) {
                roles.add(Objects.requireNonNull(EnumUtil.getByCode(i, RoleEnum.class)).getMsg());
            }
        }

        //设置该用户拥有的角色
        return new SimpleAuthorizationInfo(roles);
    }

    /**
     * 重写方法,清除当前用户的的 授权缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 自定义方法：清除所有的认证缓存和授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}