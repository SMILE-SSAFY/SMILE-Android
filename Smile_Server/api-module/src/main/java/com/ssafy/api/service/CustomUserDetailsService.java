package com.ssafy.api.service;

import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.ssafy.core.exception.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userIdx) throws UsernameNotFoundException {
        User user = null;
        try {
            user = userRepository.findById(Long.valueOf(userIdx)).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
