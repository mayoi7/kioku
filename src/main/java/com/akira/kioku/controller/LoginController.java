package com.akira.kioku.controller;

import com.akira.kioku.po.Code;
import com.akira.kioku.po.User;
import com.akira.kioku.service.CodeService;
import com.akira.kioku.service.UserService;
import com.akira.kioku.utils.EntityUtil;
import com.akira.kioku.utils.ResultUtil;
import com.akira.kioku.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * 包括登陆以及用户信息注册在内的功能
 * @author Kripath
 * @date Created in 11:28 2019/2/2
 */
@RestController
@RequestMapping("login")
@Slf4j
public class LoginController {

    private final UserService userService;

    private final CodeService codeService;

    @Autowired
    public LoginController(UserService userService, CodeService codeService) {
        this.userService = userService;
        this.codeService = codeService;
    }

    /**
     * 跳转到登陆页面
     */
    @RequestMapping("")
    public ModelAndView loginPage() {
        return new ModelAndView("login/login");
    }

    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("/login")
    public ResultVo login(@RequestParam("username") String username,
                          @RequestParam("password") String password) {

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
                return ResultUtil.error(1, "用户名错误");
            } catch (IncorrectCredentialsException ice) {
                log.warn("[登陆]用户{}密码错误", username, password);
                return ResultUtil.error(2, "密码错误");
            } catch (LockedAccountException lae) {
                log.warn("[登陆]用户{}被锁定", username);
                return ResultUtil.error(3, "账号被锁定");
            } catch (AuthenticationException ae) {
                log.warn("[登陆]用户{}发生未知异常", username);
                return ResultUtil.error(4, "未知认证异常");
            }
        }

        log.info("[登陆]用户{}正常登陆", username);
        return ResultUtil.success();
    }

    /**
     * 用户注销
     * @return 只返回成功
     */
    @GetMapping("/logout")
    public ResultVo logout() {
        Subject subject = SecurityUtils.getSubject();

        //注销
        subject.logout();
        return ResultUtil.success();
    }

    /**
     * 检测用户名是否可用
     * @param username 要检测的用户名
     * @return 返回是否可用，success为可用
     */
    @GetMapping("detect/name/{username}")
    public ResultVo usernameDetection(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if(user == null) {
            return ResultUtil.success();
        }
        return ResultUtil.error("username already exists");
    }

    /**
     * 检测邀请码是否可用
     * @param code 要检测的邀请码
     * @return 错误码1代表邀请码不存在，2代表邀请码已被使用，success表示邀请码可用
     */
    @GetMapping("detect/code/{code}")
    public ResultVo codeDetection(@PathVariable String code) {
        Code res = codeService.findByCode(code);
        if(res == null) {
            // 不存在这样的邀请码
            return ResultUtil.error(1, "nonexistent code");
        } else if(res.getUid() != null) {
            // 邀请码已被使用
            return ResultUtil.error(2, "used code");
        } else {
            return ResultUtil.success();
        }
    }

    /**
     * 进行用户的注册，在注册完毕后进行自动登陆
     * @param username 用户名
     * @param password 密码（未加密）
     * @param email 邮箱
     * @param code 邀请码
     * @return success为成功
     */
    @PostMapping("register")
    public ResultVo registerUser(
            @Size(min=6,max=20) String username,
            @Size(min=6,max=20) String password,
            @Email String email,
            String code) {
        User user = EntityUtil.encryptAndStorageAsUser(username, password, email, code);

        User record = userService.registerUser(user);
        if(record == null) {
            // 说明出现了插入异常
            return ResultUtil.error(1, "注册失败");
        }
        codeService.setUsed(code, record.getId());

        log.info("[注册]用户{}注册账号成功", username);

        // 自动执行登陆
        Subject subject = SecurityUtils.getSubject();
        // 密码是加密后的密码
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);

        try {
            subject.login(token);
        } catch (Exception e) {
            // 不可能发生用户名或密码错误以及被锁定等异常，但可能会有其他异常
            log.error("[注册]用户{}注册完毕自动登陆异常", username);
            return ResultUtil.error(2, "自动登陆失败");
        }

        return ResultUtil.success();
    }
}
