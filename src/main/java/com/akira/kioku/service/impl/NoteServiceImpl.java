package com.akira.kioku.service.impl;

import com.akira.kioku.constant.NoteConstant;
import com.akira.kioku.dto.NoteInfo;
import com.akira.kioku.dto.NotePackageable;
import com.akira.kioku.po.Content;
import com.akira.kioku.po.Note;
import com.akira.kioku.repository.ContentRepository;
import com.akira.kioku.repository.NoteRepository;
import com.akira.kioku.service.NoteService;
import com.akira.kioku.utils.EntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Kripath
 * @date Created in 15:20 2019/2/6
 */
@Service
@Slf4j
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final ContentRepository contentRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, ContentRepository contentRepository) {
        this.noteRepository = noteRepository;
        this.contentRepository = contentRepository;
    }

    @Override
    public List<NoteInfo> listAll(Long userId) {
        // 返回的是Page对象
        Page<NotePackageable> pages = noteRepository.findByUidAsInfo(userId, null);
        // 转换成List集合
        List<NotePackageable> notes = pages.getContent();
        // 再将接口封装成对象
        return EntityUtil.packageAbstractNotesToInfoList(notes);
    }

    @Override
    public List<NoteInfo> listAllInPage(Long userId, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, NoteConstant.PAGE_NUM);

        // 返回的是Page对象
        Page<NotePackageable> pages = noteRepository.findByUidAsInfo(userId, pageable);
        // 转换成List集合
        List<NotePackageable> notes = pages.getContent();
        // 再将接口封装成对象
        return EntityUtil.packageAbstractNotesToInfoList(notes);
    }

    @Transactional(rollbackFor = Exception.class)
    @RequiresRoles({"user"})
    @Override
    public void saveNote(Long userId, NoteInfo note) {
        // 先保存标题部分，从返回值获取主键
        Note noteGen = noteRepository.save(new Note(userId, note.getTitle()));
        // 再保存详细内容
        contentRepository.save(new Content(noteGen.getId(), note.getContent()));
    }

    @Override
    @RequiresRoles({"admin"})
    public Long countNote() {
        return noteRepository.count();
    }

    @Override
    public Long countNoteByUid(Long uid) {
        return noteRepository.countByUid(uid);
    }
}
