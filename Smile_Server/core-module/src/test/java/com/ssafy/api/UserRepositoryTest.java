package com.ssafy.api;

import com.ssafy.core.code.Role;
import com.ssafy.core.entity.User;
import com.ssafy.core.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("이메일 중복 체크")
    void existsEmailTest() {
        // give
        User user = User.builder()
                .email("test@email.com")
                .name("testName")
                .password("1234")
                .phoneNumber("01012345678")
                .role(Role.USER)
                .build();

        System.out.println(user.toString());
        User savedUser = userRepository.save(user);
        System.out.println("-----------------------");
        System.out.println(savedUser.toString());

        String existsEmail = "test@email.com";
        String notExistsEmail = "test1@email.com";


        // then
        Assertions.assertTrue(userRepository.existsByEmail(existsEmail));
        Assertions.assertFalse(userRepository.existsByEmail(notExistsEmail));
    }
}