package com.ssafy.api.service;

import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * JwtTokenProvider 내에서 사용할 loadUserByUsername 오버라이드 위한 클래스
 *
 * author @서재건
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * 유저 id로 유저 반환
     *
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = null;
        user = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return user;
    }
}
