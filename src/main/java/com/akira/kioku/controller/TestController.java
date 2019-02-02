package com.akira.kioku.controller;

import com.akira.kioku.utils.ResultUtil;
import com.akira.kioku.vo.ResultVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kripath
 * @date Created in 18:28 2019/2/2
 */
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("msg")
    public ResultVo getMessage() {
        return ResultUtil.success();
    }
}
