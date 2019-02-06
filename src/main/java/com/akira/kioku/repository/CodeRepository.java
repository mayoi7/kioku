package com.akira.kioku.repository;

import com.akira.kioku.po.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Kripath
 * @date Created in 22:32 2019/2/1
 */
@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {

    /**
     * 通过邀请码的值查询记录
     * @param code 邀请码的值
     * @return Code实体对象
     */
    Code findByCode(String code);
}
