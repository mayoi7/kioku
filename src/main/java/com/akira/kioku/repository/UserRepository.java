package com.akira.kioku.repository;

import com.akira.kioku.dto.UserInfo;
import com.akira.kioku.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Kripath
 * @date Created in 22:31 2019/2/1
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 查询指定用户名的用户信息
     * @param username 用户名
     * @return 不为空值表示数据库中的记录，null值表示无该用户名的用户
     */
    User findByUsername(String username);

    /**
     * 分页查询所有用户
     * @param pageable 分页类
     * @return {@link Page}集合
     */
    @Query(value = "SELECT * FROM user", nativeQuery = true)
    Page<User> listAllInPage(Pageable pageable);

}
