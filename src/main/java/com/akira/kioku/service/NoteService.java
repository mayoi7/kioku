package com.akira.kioku.service;

import com.akira.kioku.constant.NoteConstant;
import com.akira.kioku.dto.NoteInfo;

import java.util.List;

/**
 * @author Kripath
 * @date Created in 12:24 2019/2/6
 */
public interface NoteService {

    /**
     * 返回某用户的所有note，按时间倒序排序
     * @param userId 用户id
     * @return 所有note的集合
     */
    List<NoteInfo> listAll(Long userId);

    /**
     * <p>返回某用户的一页note，按时间倒序排序</p>
     * <p>页号在{@link NoteConstant}</p>中规定
     * @param userId 用户id
     * @param pageNum note的集合
     * @return 一页记录
     */
    List<NoteInfo> listAllInPage(Long userId, int pageNum);

    /**
     * 添加一条日记
     * @param userId 日记的作者用户id
     * @param note {@link NoteInfo}对象
     */
    void saveNote(Long userId, NoteInfo note);

    /**
     * 统计日记总数
     * @return 日记总数
     */
    Long countNote();

    /**
     * 统计某用户的日记总数
     * @param uid 用户id
     * @return 用户创建的日记总数
     */
    Long countNoteByUid(Long uid);
}
