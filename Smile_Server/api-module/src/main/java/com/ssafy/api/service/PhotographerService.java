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

import javax.transaction.Transactional;

/**
 * 작가 프로필 관련 클래스
 *
 * author @김정은
 */
@Service
@Transactional
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
        User user = userRepository.findById(photographer.getPhotographerId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Photographer savedPhotographer = Photographer.builder()
                .user(user)
                .profileImg(photographer.getProfileImg())
                .introduction(photographer.getIntroduction())
                .account(photographer.getAccount())
                .heart(photographer.getHeart())
                .places(photographer.getPlaces())
                .categories(photographer.getCategories())
                .build();

        photographerRepository.save(savedPhotographer);
    }

    /**
     * 작가 프로필 조회
     *
     * @param idx
     * @return 작가 프로필 객체
     */
    public PhotographerDto getPhotographer(Long idx){
        Photographer photographer = photographerRepository.findById(idx)
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));
        PhotographerDto dto = new PhotographerDto();
        return dto.of(photographer);
    }

    /**
     * 작가 프로필 수정
     *
     * @param photographer
     * @return 수정된 작가 프로필 객체
     */
    public PhotographerDto changePhotographer(PhotographerDto photographer){
        Photographer findPhotographer = photographerRepository.findById(photographer.getPhotographerId())
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));

        // 이미지가 수정이 되었을 때
        if(photographer.getProfileImg() != null){
            // TODO: 이미지 삭제 후 등록
            findPhotographer.updateProfileImg(photographer.getProfileImg());
        }

        findPhotographer.updateAccount(photographer.getAccount());
        findPhotographer.updateIntroduction(photographer.getIntroduction());
        findPhotographer.updatePlaces(photographer.getPlaces());

        PhotographerDto savedPhotographer = new PhotographerDto();
        return savedPhotographer.of(photographerRepository.save(findPhotographer));
    }

    public void removePhotographer(Long userId){
        Photographer findPhotographer = photographerRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));

        if(!findPhotographer.getProfileImg().isEmpty()) {
            // TODO: 이미지 삭제
        }
        photographerRepository.delete(findPhotographer);
    }
}
