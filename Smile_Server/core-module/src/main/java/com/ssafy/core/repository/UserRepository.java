package com.ssafy.core.repository;

import com.ssafy.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * author 김정은
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
