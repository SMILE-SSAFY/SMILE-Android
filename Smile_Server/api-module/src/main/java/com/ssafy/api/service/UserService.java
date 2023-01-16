package com.ssafy.api.service;

import com.ssafy.core.entity.User;
import com.ssafy.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 테스트 유저 메소드
     *
     * @return
     */
    public List<User> getUser(){
        return userRepository.findAll();
    }
}
