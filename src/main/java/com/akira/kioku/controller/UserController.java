package com.akira.kioku.controller;

import com.akira.kioku.dto.UserInfo;
import com.akira.kioku.po.User;
import com.akira.kioku.service.UserService;
import com.akira.kioku.utils.EntityUtil;
import com.akira.kioku.utils.ResultUtil;
import com.akira.kioku.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kripath
 * @date Created in 11:43 2019/2/1
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 测试获取用户信息
     * @return 返回成功的json
     */
    @GetMapping("msg")
    public ResultVo getMsg() {
        return ResultUtil.success();
    }

    /**
     * 获取登陆的用户名
     * @return 已登录登陆用户的用户名，如果未登陆就返回错误码
     */
    @GetMapping("name")
    public ResultVo returnUserName() {
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()) {
            // 用户未登陆
            log.warn("[查询]用户未登陆无法查询信息");
            return ResultUtil.error("未登陆的用户");
        }
        return ResultUtil.success(subject.getPrincipal());
    }

    /**
     * <p>获取包括当前登陆用户id、用户名和权限在内的用户信息</p>
     * @return {@link UserInfo}
     */
    @GetMapping("info")
    public ResultVo returnUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()) {
            // 用户未登陆
            log.warn("[查询]用户未登陆无法查询信息");
            return ResultUtil.error("未登陆的用户");
        }
        String username = subject.getPrincipal().toString();
        User user = userService.findByUsername(username);
        if(user == null) {
            log.warn("[查询]shiro保存的用户名{}有误，请核查配置", username);
        }
        return ResultUtil.success(EntityUtil.packageUserAsUserInfo(user));
    }
}
