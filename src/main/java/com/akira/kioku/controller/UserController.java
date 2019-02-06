package com.akira.kioku.controller;

import com.akira.kioku.utils.ResultUtil;
import com.akira.kioku.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
            return ResultUtil.error("未登陆的用户");
        }
        return ResultUtil.success(subject.getPrincipal());
    }
}
