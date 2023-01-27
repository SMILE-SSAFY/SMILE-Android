package com.ssafy.api.service;

import com.ssafy.api.dto.Photographer.CategoriesReqDto;
import com.ssafy.api.dto.Photographer.PhotographerForListDto;
import com.ssafy.api.dto.Photographer.PhotographerHeartDto;
import com.ssafy.api.dto.Photographer.PhotographerReqDto;
import com.ssafy.api.dto.Photographer.PhotographerResDto;
import com.ssafy.api.dto.Photographer.PhotographerUpdateReqDto;
import com.ssafy.api.dto.Photographer.PlacesReqDto;
import com.ssafy.core.code.Role;
import com.ssafy.core.entity.Categories;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.PhotographerHeart;
import com.ssafy.core.entity.PhotographerNCategories;
import com.ssafy.core.entity.PhotographerNPlaces;
import com.ssafy.core.entity.Places;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.PhotographerHeartRepository;
import com.ssafy.core.repository.PhotographerNCategoriesRepository;
import com.ssafy.core.repository.PhotographerNPlacesRepository;
import com.ssafy.core.repository.PhotographerRepository;
import com.ssafy.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 작가 프로필 관련 클래스
 *
 * @author 김정은
 * @author 서재건
 * @author 신민철
 */
@Service
@Transactional
@Slf4j
public class PhotographerService {
    @Autowired
    private PhotographerNCategoriesRepository photographerNCategoriesRepository;
    @Autowired
    private PhotographerNPlacesRepository photographerNPlacesRepository;
    @Autowired
    private PhotographerRepository photographerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private S3UploaderService s3UploaderService;
    @Autowired
    private PhotographerHeartRepository photographerHeartRepository;

    /**
     * 작가 등록
     *
     * @param multipartFile 프로필 이미지
     * @param photographer
     * @throws USER_NOT_FOUND 유저를 찾을 수 없을 때 에러
     */
    public void addPhotographer(MultipartFile multipartFile, PhotographerReqDto photographer) throws IOException{
        User user = userRepository.findById(photographer.getPhotographerId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(!multipartFile.isEmpty()) {
            // 파일 업로드
            String fileName = s3UploaderService.upload(multipartFile);
            photographer.setProfileImg(fileName);
        }

        // 활동지역 변환
        List<PhotographerNPlaces> places = new ArrayList<>();
        for(PlacesReqDto place : photographer.getPlaces()){
            log.info(place.getPlaceId().getClass().getName());
            places.add(PhotographerNPlaces.builder()
                    .photographer(Photographer.builder().id(user.getId()).build())
                    .places(Places.builder().id(place.getPlaceId()).build())
                    .build()
            );
            log.info(places.get(0).getPlaces().getId());
        }

        // 카테고리 변환
        List<PhotographerNCategories> categories = new ArrayList<>();
        for(CategoriesReqDto category : photographer.getCategories()){
            categories.add(PhotographerNCategories.builder()
                    .photographer(Photographer.builder().id(user.getId()).build())
                    .category(Categories.builder().id(category.getCategoryId()).build())
                    .price(category.getPrice())
                    .description(category.getDescription())
                    .build()
            );
        }

        Photographer savedPhotographer = Photographer.builder()
                .user(user)
                .profileImg(photographer.getProfileImg())
                .introduction(photographer.getIntroduction())
                .account(photographer.getAccount())
                .places(places)
                .categories(categories)
                .build();

        photographerRepository.save(savedPhotographer);

        // 유저 역할 photographer로 수정
        user.updateRole(Role.PHOTOGRAPHER);
        userRepository.save(user);
    }

    /**
     * 작가 프로필 조회
     *
     * @param idx
     * @return 작가 프로필 객체
     * @throws PHOTOGRAPHER_NOT_FOUND 사진작가를 찾을 수 없을 때 에러
     */
    public PhotographerResDto getPhotographer(Long idx){
        Photographer photographer = photographerRepository.findById(idx)
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));
        PhotographerResDto dto = new PhotographerResDto();
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
    public PhotographerResDto changePhotographer(MultipartFile file, PhotographerUpdateReqDto photographer) throws IOException {
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

        // 활동지역 변환
        for(PhotographerNPlaces places : findPhotographer.getPlaces()){
            photographerNPlacesRepository.deleteById(places.getId());
        }
        List<PhotographerNPlaces> places = new ArrayList<>();
        for(PlacesReqDto place : photographer.getPlaces()){
            places.add(PhotographerNPlaces.builder()
                    .photographer(Photographer.builder().id(photographer.getPhotographerId()).build())
                    .places(Places.builder().id(place.getPlaceId()).build())
                    .build()
            );
        }

        // 카테고리 변환
        for(PhotographerNCategories categories : findPhotographer.getCategories()){
            photographerNCategoriesRepository.deleteById(categories.getId());
        }
        List<PhotographerNCategories> categories = new ArrayList<>();
        for(CategoriesReqDto category : photographer.getCategories()){
            categories.add(PhotographerNCategories.builder()
                    .photographer(Photographer.builder().id(photographer.getPhotographerId()).build())
                    .category(Categories.builder().id(category.getCategoryId()).build())
                    .price(category.getPrice())
                    .description(category.getDescription())
                    .build()
            );
        }

        findPhotographer.updateProfileImg(photographer.getProfileImg());
        findPhotographer.updateAccount(photographer.getAccount());
        findPhotographer.updateIntroduction(photographer.getIntroduction());
        findPhotographer.updatePlaces(places);
        findPhotographer.updateCategories(categories);

        PhotographerResDto savedPhotographer = new PhotographerResDto();
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

        // 일반 유저로 전환
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.updateRole(Role.USER);
        userRepository.save(user);
    }

    /**
     * categoryId로 작가 조회
     * 
     * TODO: 작가 좋아요 구현 시 좋아요 갯수 및 좋아요 상태 추가, 추가 dto 필요
     * 
     * @param categoryId
     * @return List<PhotographerForListDto>
     * @throws PHOTOGRAPHER_NOT_FOUND 사진작가를 찾을 수 없을 때 에러
     */
    public List<PhotographerForListDto> getPhotographerListByCategory(Long categoryId) {
        List<Photographer> photographerList = photographerNCategoriesRepository.findByCategoryId(categoryId);
        log.info("카테고리로 작가 조회");

        if (photographerList.isEmpty()) {
            log.info("해당 카테고리의 작가가 없음");
            throw new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND);
        }

        log.info("해당 카테고리를 가진 작가가 있음");
        List<PhotographerForListDto> photographerForList = new ArrayList<>();
        for (Photographer photographer : photographerList) {
            photographerForList.add(new PhotographerForListDto().of(photographer));
        }
        
        return photographerForList;
    }

    /***
     *
     * @param photographerId
     * @return 포토그래퍼 id, isHeart boolean
     * @throws CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND)
     * @throws CustomException(ErrorCode.USER_NOT_FOUND)
     */
    public PhotographerHeartDto addHeartPhotographer(Long photographerId){
        Photographer photographer = photographerRepository.findById(photographerId).orElseThrow(()-> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        User user = userRepository.findByEmail(username).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));
        boolean isHeart = isHearted(user, photographer);

        // 좋아요가 없을 때 -> 좋아요 등록
        if(!isHeart){
            photographerHeartRepository.save(new PhotographerHeart(user, photographer));
        } else {
            photographerHeartRepository.deleteByUserAndPhotographer(user, photographer);
        }

        // 버튼을 누른 이후 이므로, 좋아요면 싫어요, 싫어요면 좋아요를 반환
        return PhotographerHeartDto.builder()
                .photographerId(photographerId)
                .isHeart(!isHeart)
                .build();
    }

    /***
     *
     * @param user
     * @param photographer
     * @return user가 photographer에 좋아요를 눌렀는지 여부
     */
    private Boolean isHearted(User user, Photographer photographer){
        return photographerHeartRepository.findByUserAndPhotographer(user, photographer).isPresent();
    }
}
