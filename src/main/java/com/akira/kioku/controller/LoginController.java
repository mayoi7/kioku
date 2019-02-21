package com.akira.kioku.controller;

import com.akira.kioku.constant.EmailConstant;
import com.akira.kioku.constant.WebConstant;
import com.akira.kioku.dto.ExpireVerify;
import com.akira.kioku.po.Code;
import com.akira.kioku.po.User;
import com.akira.kioku.service.CodeService;
import com.akira.kioku.service.UserService;
import com.akira.kioku.utils.EntityUtil;
import com.akira.kioku.utils.MailUtil;
import com.akira.kioku.utils.ResultUtil;
import com.akira.kioku.utils.TokenUtil;
import com.akira.kioku.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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

    private final MailUtil mailUtil;

    @Autowired
    public LoginController(UserService userService, CodeService codeService, MailUtil mailUtil) {
        this.userService = userService;
        this.codeService = codeService;
        this.mailUtil = mailUtil;
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
                log.warn("[登陆] 用户名错误，用户名为{}", username);
                return ResultUtil.error(1, "用户名错误");
            } catch (IncorrectCredentialsException ice) {
                log.warn("[登陆] 用户{}密码错误", username, password);
                return ResultUtil.error(2, "密码错误");
            } catch (LockedAccountException lae) {
                log.warn("[登陆] 用户{}被锁定", username);
                return ResultUtil.error(3, "账号被锁定");
            } catch (AuthenticationException ae) {
                log.warn("[登陆] 用户{}发生未知异常", username);
                return ResultUtil.error(4, "未知认证异常");
            }
        }

        log.info("[登陆] 用户-{} 正常登陆", username);
        return ResultUtil.success();
    }

    /**
     * 用户注销
     * @return 只返回成功
     */
    @GetMapping("/logout")
    public ModelAndView logout() {
        Subject subject = SecurityUtils.getSubject();

        //注销
        subject.logout();
        // 跳转到登陆页
        return new ModelAndView("redirect:/login");
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
        return ResultUtil.error("已被使用的用户名");
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
            return ResultUtil.error(1, "不存在的邀请码");
        } else if(res.getUid() != null) {
            // 邀请码已被使用
            return ResultUtil.error(2, "被使用的邀请码");
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

        log.info("[注册] 用户-{} 注册账号成功", username);

        // 自动执行登陆
        Subject subject = SecurityUtils.getSubject();
        // 密码是加密后的密码
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);

        try {
            subject.login(token);
        } catch (Exception e) {
            // 不可能发生用户名或密码错误以及被锁定等异常，但可能会有其他异常
            log.error("[注册] 用户-{} 注册完毕自动登陆异常", username);
            return ResultUtil.error(2, "自动登陆失败");
        }

        return ResultUtil.success();
    }

    /**
     * 忘记密码
     * @param username 请求重置密码用户的用户名
     * @param session 保存在服务器存储的超时验证对象(key值为ds)
     * @return 结果码
     */
    @PostMapping("/forget/{username}")
    public ResultVo forgetPassword(@PathVariable("username") String username, HttpSession session) {
        User user = userService.findByUsername(username);
        if(user == null) {
            log.warn("[密码重置] 用户名-{} 不存在", username);
            return ResultUtil.error("用户名不存在");
        } else if(user.getEmail() == null) {
            log.warn("[密码重置] 用户-{} 没有填写邮箱", username);
            return ResultUtil.error("没有填写邮箱无法找回");
        }

        String email = user.getEmail();

        // 重置链接过期时间(时间戳形式)
        Long expireDate = System.currentTimeMillis() + EmailConstant.URL_EFFECTIVE_TIME;
        // 生成随机密钥
        String secretKey = TokenUtil.makeUUID();
        // 生成数字签名
        String digitalSignature = TokenUtil.makeDigitalSignature(username, expireDate, secretKey);
        // 生成超时验证的对象（保存超时时间和密钥）
        ExpireVerify expireVerify = new ExpireVerify(expireDate, secretKey);
        // 设置到session中
        session.setAttribute("ds", expireVerify);

        // 发送重置密码的邮件
//        mailUtil.sendResetPasswordMail(email,
//                WebConstant.HOSTNAME + "/login/reset/" + username + "/" + digitalSignature);

        log.info("[密码重置] 已向 邮箱-{} 发送 用户-{} 的密码重置链接，", email, username);
        return ResultUtil.success(digitalSignature);
    }

    /**
     * 重置密码，通过session中的超时验证与客户端传来的的进行对比，
     * 如果合法并一致则跳转到重置页面
     * @param username 被重置密码用户的用户名
     * @param serviceDS 客户端数字签名
     * @param session 保存在服务器存储的超时验证对象(key值为ds)
     * @return 结果码
     */
    @GetMapping("/reset/password/{username}/{ds}")
    public ModelAndView resetPassword(@PathVariable("username") String username,
                                  @PathVariable("ds") String serviceDS, HttpSession session) {
        // 从session中获取服务器端存储的超时验证对象
        ExpireVerify servletEV = (ExpireVerify) session.getAttribute("ds");
        if(servletEV == null) {
            log.warn("[密码重置] 服务器未存储签名，用户-{} 可能未请求重置密码", username);
            return new ModelAndView("error/error");
        }

        // 生成服务器端数字签名进行比对
        String servletDS = TokenUtil.makeDigitalSignature(username,
                servletEV.getExpireDate(), servletEV.getSecretKey());

        if(!servletDS.equals(serviceDS)) {
            log.warn("[密码重置] 用户-{} 数字签名校验不一致", username);
            return new ModelAndView("error/error");
        }

        Long currentDate = System.currentTimeMillis();
        if(currentDate - servletEV.getExpireDate() > 0) {
            // 超出时限
            log.warn("[密码重置] 用户-{} 密码重置链接超时失效");
            return new ModelAndView("error/error");
        }

        log.info("[密码重置] 用户-{} 数字签名校验通过", username);
        // 跳转到密码重置页
        return new ModelAndView("login/register");
    }

}
