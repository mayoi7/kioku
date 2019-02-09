package com.akira.kioku.controller;

import com.akira.kioku.constant.NoteConstant;
import com.akira.kioku.dto.NoteInfo;
import com.akira.kioku.service.NoteService;
import com.akira.kioku.utils.ResultUtil;
import com.akira.kioku.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 关于note的操作
 * @author Kripath
 * @date Created in 10:07 2019/2/6
 */
@RestController
@RequestMapping("note")
@Slf4j
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

//    /**
//     * 返回一页记录，每页数量在常量类{@link NoteConstant}中记录
//     * @param userId 所要查询的用户id
//     * @param page 页号，默认为0
//     * @return 查询的一页的结果
//     */
//    @GetMapping("all/{userId}")
//    public ResultVo returnAll(@PathVariable("userId") Long userId,
//                              @RequestParam(defaultValue = "0") Integer page) {
//        List<NoteInfo> notes = noteService.listAllInPage(userId, page);
//        log.info("[日记]获取id为{}用户的第{}页记录", userId, page);
//        return ResultUtil.success(notes);
//    }

    /**
     * 返回用户所有记录
     * @param userId 所要查询的用户id
     * @return 查询的所有结果
     */
    @GetMapping("all/{userId}")
    public ResultVo returnAll(@PathVariable("userId") Long userId) {
        List<NoteInfo> notes = noteService.listAll(userId);
        log.info("[日记]获取id为{}用户的所有记录", userId);
        return ResultUtil.success(notes);
    }

    @PostMapping("/{userId}")
    public ResultVo saveNote(@PathVariable("userId") Long userId, NoteInfo note) {
        // FIXME: 换行符存储无效
        noteService.saveNote(userId, note);
        return ResultUtil.success();
    }
}
