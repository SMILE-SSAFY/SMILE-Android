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
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

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

    @Autowired
    private S3UploaderService s3UploaderService;

    /**
     * 작가 등록
     *
     * @param photographer
     * @throws USER_NOT_FOUND 유저를 찾을 수 없을 때 에러
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
     * @throws PHOTOGRAPHER_NOT_FOUND 사진작가를 찾을 수 없을 때 에러
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
     * @param file 이미지 파일
     * @param photographer
     * @return 수정된 작가 프로필 객체
     * @throws PHOTOGRAPHER_NOT_FOUND 사진작가를 찾을 수 없을 때 에러
     * @throws IOException
     */
    public PhotographerDto changePhotographer(MultipartFile file, PhotographerDto photographer) throws IOException {
        Photographer findPhotographer = photographerRepository.findById(photographer.getPhotographerId())
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));

        log.info(photographer.getProfileImg());
        log.info(findPhotographer.getProfileImg());

        if(!photographer.isDeleted()){
            if (!file.isEmpty()) {  // 이미지가 수정되었을 때
                // 이미지 삭제
                s3UploaderService.deleteFile(findPhotographer.getProfileImg().trim());
                String fileName = s3UploaderService.upload(file);
                photographer.setProfileImg(fileName);
            } else {    // 이미지가 수정되지 않았을 때
                photographer.setProfileImg(findPhotographer.getProfileImg());
            }
        } else {    // 이미지가 삭제되었을 때
            s3UploaderService.deleteFile(findPhotographer.getProfileImg().trim());
        }

        findPhotographer.updateProfileImg(photographer.getProfileImg());
        findPhotographer.updateAccount(photographer.getAccount());
        findPhotographer.updateIntroduction(photographer.getIntroduction());
        findPhotographer.updatePlaces(photographer.getPlaces());
        findPhotographer.updateCategories(photographer.getCategories());

        PhotographerDto savedPhotographer = new PhotographerDto();
        return savedPhotographer.of(photographerRepository.save(findPhotographer));
    }

    /**
     * 사진 작가 프로필 삭제
     *
     * @param userId 사진작가 인덱스 번호
     * @throws PHOTOGRAPHER_NOT_FOUND 사진작가를 찾을 수 없을 때 에러
     */
    public void removePhotographer(Long userId){
        Photographer findPhotographer = photographerRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));

        // 이미지 삭제
        if(findPhotographer.getProfileImg() != null) {
            s3UploaderService.deleteFile(findPhotographer.getProfileImg().trim());
        }
        photographerRepository.delete(findPhotographer);
    }
}
