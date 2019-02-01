package com.akira.kioku.repository;

import com.akira.kioku.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Kripath
 * @date Created in 22:31 2019/2/1
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
