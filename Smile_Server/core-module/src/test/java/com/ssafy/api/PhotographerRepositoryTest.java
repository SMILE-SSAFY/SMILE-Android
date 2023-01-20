package com.ssafy.api;

import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.Places;
import com.ssafy.core.entity.User;
import com.ssafy.core.repository.PhotographerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest(properties = "spring.config.location = classpath:/application.yml,classpath:/application-real.yml,classpath:/application-aws.yml")
public class PhotographerRepositoryTest {
    @Autowired
    private PhotographerRepository photographerRepository;

    @DisplayName("사진작가 등록")
    @Test
    void addPhotographer(){
        //given
        Places places = Places.builder()
                .place("경상북도 구미시")
                .build();

        List<Places> list = new ArrayList<>();
        list.add(places);

        Photographer photographer = Photographer.builder()
                .id(1L)
                .user(User.builder().email("aaa@gmail.com").build())
                .profileImg("http://noimg")
                .introduction("저는 사진을 잘 찍어요.")
                .account("1111111111111")
                .heart(0)
                .places(list)
                .build();
        //when
        Photographer saved = photographerRepository.save(photographer);

        //then
        Assertions.assertEquals(saved.getProfileImg(), "http://noimg");
    }
}