package com.akira.kioku.repository;

import com.akira.kioku.dto.NoteInfo;
import com.akira.kioku.dto.NotePackageable;
import com.akira.kioku.po.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Kripath
 * @date Created in 22:32 2019/2/1
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    /**
     * 查询某用户的所有note，按时间倒序排序
     * @param uid 作者id
     * @param pageable 分页类
     * @return {@link NotePackageable}的集合
     */
    @Query(value = "SELECT note.id, note.title, content, note.gmt_create gmtCreate " +
            "FROM note, content WHERE note.id = content.nid AND note.uid = 2 " +
            "ORDER BY note.id DESC", nativeQuery = true)
    Page<NotePackageable> findByUidAsInfo(@Param("uid") Long uid, Pageable pageable);
}
