package com.akira.kioku.repository;

import com.akira.kioku.dto.NoteInfo;
import com.akira.kioku.dto.NotePackageable;
import com.akira.kioku.po.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Kripath
 * @date Created in 22:32 2019/2/1
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query(value = "SELECT a.id, a.title, b.content, a.gmt_create gmtCreate " +
            "FROM note a, content b " +
            "WHERE a.id = b.nid AND a.id = :id", nativeQuery = true)
    NotePackageable findByIdAsInfo(@Param("id") Long id);
}
