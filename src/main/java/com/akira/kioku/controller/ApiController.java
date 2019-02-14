package com.akira.kioku.controller;

import com.akira.kioku.dto.UserDetail;
import com.akira.kioku.service.UserService;
import com.akira.kioku.utils.ResultUtil;
import com.akira.kioku.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 开放的接口
 * @author Kripath
 * @date Created in 18:27 2019/2/14
 */
@RestController
@RequestMapping("api")
public class ApiController {

    private final UserService userService;

    @Autowired
    public ApiController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 通过用户名查询某用户的详细信息，不包含敏感信息
     * @param username 用户名
     * @return {@link UserDetail}对象
     */
    @GetMapping("/user/{username}")
    public ResultVo queryUserDetailInfo(@PathVariable("username") String username) {
        UserDetail detail = userService.queryUserDetailByUsername(username);

        return ResultUtil.success(detail);
    }
}
