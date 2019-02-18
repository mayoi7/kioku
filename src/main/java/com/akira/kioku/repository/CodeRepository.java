package com.akira.kioku.repository;

import com.akira.kioku.po.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 按时间倒序查找所有邀请码信息
     * @return Code集合
     */
    @Query(value = "select * from code order by id desc", nativeQuery = true)
    List<Code> findAllOrderByIdDesc();
}
