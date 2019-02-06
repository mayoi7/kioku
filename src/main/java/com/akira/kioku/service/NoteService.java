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
     * @return
     */
    List<NoteInfo> listAllInPage(Long userId, int pageNum);
}
