package com.akira.kioku.repository;

import com.akira.kioku.po.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Kripath
 * @date Created in 9:52 2019/2/2
 */
@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
}
