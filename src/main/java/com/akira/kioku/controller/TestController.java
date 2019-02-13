package com.akira.kioku.controller;

import com.akira.kioku.utils.ResultUtil;
import com.akira.kioku.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

/**
 * @author Kripath
 * @date Created in 18:28 2019/2/2
 */
@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    @GetMapping("msg")
    public ResultVo getMessage() {
        return ResultUtil.success();
    }

    @GetMapping("/count")
    public String number(HttpServletResponse resp, HttpSession session){
        return "";
    }
}
