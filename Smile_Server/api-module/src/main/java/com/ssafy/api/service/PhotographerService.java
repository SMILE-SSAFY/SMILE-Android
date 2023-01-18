package com.ssafy.api.service;

import com.ssafy.api.dto.PhotographerDto;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.PhotographerRepository;
import com.ssafy.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PhotographerService {
    @Autowired
    private PhotographerRepository photographerRepository;

    @Autowired
    private UserRepository userRepository;

    public void addPhotographer(PhotographerDto photographer){
        User user = userRepository.findById(photographer.getUserIdx())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Photographer savedPhotographer = Photographer.builder()
                .id(photographer.getUserIdx())
                .user(user)
                .profileImg(photographer.getProfileImg())
                .introduction(photographer.getIntroduction())
                .account(photographer.getAccount())
                .heart(photographer.getHeart())
                .places(photographer.getPlaces())
                .build();

        photographerRepository.save(savedPhotographer);
    }

}
