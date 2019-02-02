package com.akira.kioku.controller;

import com.akira.kioku.utils.ResultUtil;
import com.akira.kioku.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
     * 跳转到登陆页面
     */
    @RequestMapping("login")
    public String loginPage() {
        return "login/login";
    }

}
