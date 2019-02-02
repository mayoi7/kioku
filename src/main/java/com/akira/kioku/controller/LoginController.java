package com.akira.kioku.controller;

import com.akira.kioku.service.UserService;
import com.akira.kioku.utils.ResultUtil;
import com.akira.kioku.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 包括登陆以及用户信息注册在内
 * @author Kripath
 * @date Created in 11:28 2019/2/2
 */
@RestController
@RequestMapping("login")
@Slf4j
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("/login")
    public ResultVo login(String username, String password) {

        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()) {
            // 如果未认证
            // 在认证提交前准备 token（令牌）
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            // 执行认证登陆
            try {
                subject.login(token);
            } catch (UnknownAccountException uae) {
                log.warn("[登陆]用户名错误，用户名为{}", username);
                return ResultUtil.error("用户名错误");
            } catch (IncorrectCredentialsException ice) {
                log.warn("[登陆]用户{}密码错误", username, password);
                return ResultUtil.error("密码错误");
            } catch (LockedAccountException lae) {
                log.warn("[登陆]用户{}被锁定", username);
                return ResultUtil.error("账号被锁定");
            } catch (AuthenticationException ae) {
                log.warn("[登陆]用户{}发生未知异常", username);
                return ResultUtil.error("未知认证异常");
            }
        }

        log.info("[登陆]用户{}正常登陆", username);
        return ResultUtil.success();
    }

    @GetMapping("/logout")
    public ResultVo logout() {
        Subject subject = SecurityUtils.getSubject();

        //注销
        subject.logout();
        return ResultUtil.success();
    }
}
