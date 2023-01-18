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

/**
 * 작가 프로필 관련 클래스
 *
 * author @김정은
 */
@Service
@Slf4j
public class PhotographerService {
    @Autowired
    private PhotographerRepository photographerRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 작가 등록
     *
     * @param photographer
     */
    public void addPhotographer(PhotographerDto photographer){
        User user = userRepository.findById(photographer.getPhotographerIdx())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Photographer savedPhotographer = Photographer.builder()
                .id(photographer.getPhotographerIdx())
                .user(user)
                .profileImg(photographer.getProfileImg())
                .introduction(photographer.getIntroduction())
                .account(photographer.getAccount())
                .heart(photographer.getHeart())
                .places(photographer.getPlaces())
                .build();

        photographerRepository.save(savedPhotographer);
    }

    public PhotographerDto getPhotographer(Long idx){
        Photographer photographer = photographerRepository.findById(idx)
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));
        PhotographerDto dto = new PhotographerDto();
        return dto.of(photographer);
    }
}
