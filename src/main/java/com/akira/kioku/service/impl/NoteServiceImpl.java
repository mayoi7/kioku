package com.akira.kioku.service.impl;

import com.akira.kioku.constant.NoteConstant;
import com.akira.kioku.dto.NoteInfo;
import com.akira.kioku.dto.NotePackageable;
import com.akira.kioku.repository.NoteRepository;
import com.akira.kioku.service.NoteService;
import com.akira.kioku.utils.EntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kripath
 * @date Created in 15:20 2019/2/6
 */
@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
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
}
