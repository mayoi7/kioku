package com.akira.kioku.controller;

import com.akira.kioku.utils.ResultUtil;
import com.akira.kioku.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
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
}
