package com.akira.kioku.controller;

import com.akira.kioku.service.NoteService;
import com.akira.kioku.service.UserService;
import com.akira.kioku.shiro.ShiroSessionListener;
import com.akira.kioku.utils.ResultUtil;
import com.akira.kioku.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * 管理员的一系列操作
 * @author Kripath
 * @date Created in 12:09 2019/2/2
 */
@RestController
@RequestMapping("admin")
@Slf4j
public class AdminController {

    private final ShiroSessionListener shiroSessionListener;

    private final UserService userService;

    private final NoteService noteService;

    @Autowired
    public AdminController(ShiroSessionListener shiroSessionListener,
                           UserService userService, NoteService noteService) {
        this.shiroSessionListener = shiroSessionListener;
        this.userService = userService;
        this.noteService = noteService;
    }

    /**
     * 测试获取管理员信息
     * @return 返回成功的json
     */
    @GetMapping("msg")
    public ResultVo getMsg() {
        return ResultUtil.success();
    }

    /**
     * 跳转到管理员页面
     */
    @RequestMapping("")
    public ModelAndView goAdminPage() {
        return new ModelAndView("admin/admin");
    }

    /**
     * 统计在线人数，通过session来统计
     */
    @GetMapping("/count/online")
    public Long countOnline() {
        return (long) shiroSessionListener.getSessionCount().get();
    }

    /**
     * 统计注册人数
     */
    @GetMapping("/count/register")
    public Long countRegister() {
        return userService.countRegisterUser();
    }

    /**
     * 统计日记总数
     * @return 数据库中note表的记录总数
     */
    @GetMapping("/count/note")
    public Long countNote() {
        return noteService.countNote();
    }



}
