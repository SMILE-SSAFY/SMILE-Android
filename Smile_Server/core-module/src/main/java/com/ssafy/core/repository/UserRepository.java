package com.ssafy.core.repository;

import com.ssafy.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * author @김정은
 * author @서재건
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 유저 이메일로 유저 반환
     *
     * @param email
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);

    /**
     * 유저 테이블 내 같은 이메일 존재 유무
     * true 존재
     * false 없음
     *
     * @param email
     * @return boolean
     */
    boolean existsByEmail(String email);

    /**
     * 유저 테이블 내 같은 닉네임 존재 유무
     * true 존재
     * false 없음
     *
     * @param nickname
     * @return boolean
     */
    boolean existsByNickname(String nickname);
}
