package com.ssafy.core.repository;

import com.ssafy.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 회원 관련 Repository
 *
 * @author 김정은
 * @author 서재건
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
     * 유저 테이블 내 이메일 중복 체크
     *
     * @param email
     * @return
     * true 존재
     * false 없음
     */
    boolean existsByEmail(String email);

    /**
     * 휴대폰 번호 존재 유무 체크
     *
     * @param phoneNumber
     * @return User
     */
    boolean existsByPhoneNumber(String phoneNumber);

}
