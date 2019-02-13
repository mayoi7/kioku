package com.akira.kioku.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 控制主页及其他一部分页面的跳转
 * @author Kripath
 * @date Created in 12:02 2019/2/1
 */
@Controller
@Slf4j
public class BaseController {

    @RequestMapping("")
    public String index() {
        return "index";
    }

    /**
     * 跳转到注册页面
     */
    @RequestMapping("page/register")
    public String goRegisterPage() {
        return "login/register";
    }

    @RequiresRoles({"user"})
    @RequestMapping("page/browse")
    public String goBrowsePage() {
        return "option/browse";
    }

    @RequiresRoles({"user"})
    @RequestMapping("page/add")
    public String goAddPage() {
        return "option/add";
    }
}
