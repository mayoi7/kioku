package com.akira.kioku.controller;

import com.akira.kioku.constant.UserConstant;
import com.akira.kioku.dto.CodeInfo;
import com.akira.kioku.dto.Pager;
import com.akira.kioku.dto.UserDetail;
import com.akira.kioku.service.CodeService;
import com.akira.kioku.service.NoteService;
import com.akira.kioku.service.UserService;
import com.akira.kioku.shiro.ShiroSessionListener;
import com.akira.kioku.utils.ResultUtil;
import com.akira.kioku.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    private final CodeService codeService;

    @Autowired
    public AdminController(ShiroSessionListener shiroSessionListener,
                           UserService userService, NoteService noteService, CodeService codeService) {
        this.shiroSessionListener = shiroSessionListener;
        this.userService = userService;
        this.noteService = noteService;
        this.codeService = codeService;
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
            log.warn("[统计] user表中无记录");
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
            log.warn("[查询] 不合法的参数: page={}", page);
            return ResultUtil.error("不合法的页码");
        }
        List<UserDetail> details = userService.listAllDetailInPage(page-1);
        return ResultUtil.success(details);
    }

    /**
     * 删除用户
     * @param username 要删除用户的用户名
     * @return 删除是否成功的标识
     */
    @DeleteMapping("/user/{username}")
    public ResultVo deleteUser(@PathVariable("username") String username) {
        log.info("[删除] 请求删除 用户-{}", username);
        if(userService.deleteByUsername(username)) {
            log.info("[删除] 删除 用户-{} 成功", username);
            return ResultUtil.success();
        }
        log.warn("[删除] 删除 用户-{} 失败，数据库中无记录", username);
        return ResultUtil.error("无该用户");
    }

    /**
     * 锁定一个用户(无法进行任何的写入或更新操作)
     * @param username 被锁定用户的用户名
     * @return 是否成功的标识
     */
    @PostMapping("/lock/{username}")
    public ResultVo lockUser(@PathVariable("username")String username) {
        log.info("[锁定] 请求锁定 用户-{}", username);
        if(userService.lockByUsername(username)) {
            log.info("[锁定] 锁定 用户-{} 成功", username);
            return ResultUtil.success();
        }
        log.warn("[锁定] 锁定 用户-{} 失败，数据库中无记录", username);
        return ResultUtil.error("无该用户");
    }

    /**
     * 重置一个用户
     * @param username 被重置的用户名
     * @return 是否成功的标识
     */
    @PostMapping("/reset/{username}")
    public ResultVo resetUser(@PathVariable("username") String username) {
        log.info("[重置] 请求重置 用户-{}", username);
        if(userService.resetByUsername(username)) {
            log.info("[重置] 重置 用户-{} 成功", username);
            return ResultUtil.success();
        }
        log.warn("[重置]重置 用户-{} 失败，数据库中无记录", username);
        return ResultUtil.error("无该用户");
    }

    @PostMapping("/authorize/{username}")
    public ResultVo authorizeUser(@PathVariable("username") String username) {
        log.info("[授权] 请求授权 用户-{}", username);
        if(userService.authorizeByUsername(username)) {
            log.info("[授权] 授权 用户-{} 成功", username);
            return ResultUtil.success();
        }
        log.warn("[授权]授权 用户-{} 失败，数据库中无记录", username);
        return ResultUtil.error("无该用户");
    }

    /**
     * 返回所有邀请码的信息，按时间倒序排列
     * @return {@link CodeInfo}的集合
     */
    @GetMapping("/codes")
    public ResultVo returnCodesInfo() {
        List<CodeInfo> codes = codeService.listAllAsCodeInfo();
        return ResultUtil.success(codes);
    }

}
