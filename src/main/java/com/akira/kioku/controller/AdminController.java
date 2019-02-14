package com.akira.kioku.controller;

import com.akira.kioku.constant.UserConstant;
import com.akira.kioku.dto.Pager;
import com.akira.kioku.dto.UserDetail;
import com.akira.kioku.service.NoteService;
import com.akira.kioku.service.UserService;
import com.akira.kioku.shiro.ShiroSessionListener;
import com.akira.kioku.utils.ResultUtil;
import com.akira.kioku.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.util.List;

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
    public ResultVo countOnline() {
        return ResultUtil.success((long) shiroSessionListener.getSessionCount().get());
    }

    /**
     * 统计注册人数
     */
    @GetMapping("/count/user")
    public ResultVo countRegister() {
        return ResultUtil.success(userService.countUser());
    }

    /**
     * 统计日记总数
     * @return 数据库中note表的记录总数
     */
    @GetMapping("/count/note")
    public ResultVo countNote() {
        return ResultUtil.success(noteService.countNote());
    }

    /**
     * 返回分页类用于初始化分页下标
     * @return 包含初始页码、页面数据、总页数的{@link Pager}对象
     */
    @GetMapping("/count/user/page")
    public ResultVo returnUserPager() {
        Long count = userService.countUser();
        if(count == null) {
            log.warn("[统计]user表中无记录");
            return ResultUtil.error();
        }
        // 总页码数(初始页码为1)
        Long page = count / UserConstant.PAGE_NUM + 1;
        int[] pageNum = {1, 2, 3, 4, 5};
        if(page < UserConstant.MAX_PAGE_NUM_PRE_PAGE) {
            pageNum = new int[page.intValue()];
            for (int i = 0; i < page; i++) {
                pageNum[i] = i + 1;
            }
        }
        Pager pager = new Pager(page, 1, pageNum);
        return ResultUtil.success(pager);
    }

    /**
     * 返回指定页码数的用户详细信息列表
     * @param page 页号（第一页页号为1）
     * @return 指定页码的UserDetail集合
     */
    @GetMapping("query/user/{page}")
    public ResultVo listAllUserDetails(@PathVariable("page")Integer page) {
        if(page < 1) {
            log.warn("[查询]不合法的参数: page={}", page);
            return ResultUtil.error("不合法的页码");
        }
        List<UserDetail> details = userService.listAllDetailInPage(page-1);
        return ResultUtil.success(details);
    }
}
