package com.ssafy.api.service;

import com.ssafy.api.dto.Photographer.CategoriesReqDto;
import com.ssafy.api.dto.Photographer.PhotographerForListDto;
import com.ssafy.api.dto.Photographer.PhotographerHeartDto;
import com.ssafy.api.dto.Photographer.PhotographerNearDto;
import com.ssafy.api.dto.Photographer.PhotographerReqDto;
import com.ssafy.api.dto.Photographer.PhotographerResDto;
import com.ssafy.api.dto.Photographer.PhotographerUpdateReqDto;
import com.ssafy.api.dto.Photographer.PlacesReqDto;
import com.ssafy.api.dto.article.PhotographerInfoDto;
import com.ssafy.core.code.Role;
import com.ssafy.core.dto.PhotographerQdslDto;
import com.ssafy.core.dto.ReviewQdslDto;
import com.ssafy.core.entity.Article;
import com.ssafy.core.entity.Categories;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.PhotographerHeart;
import com.ssafy.core.entity.PhotographerNCategories;
import com.ssafy.core.entity.PhotographerNPlaces;
import com.ssafy.core.entity.Places;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.CategoriesRepository;
import com.ssafy.core.repository.ReviewRepository;
import com.ssafy.core.repository.UserRepository;
import com.ssafy.core.repository.article.ArticleRepository;
import com.ssafy.core.repository.photographer.PhotographerHeartRepository;
import com.ssafy.core.repository.photographer.PhotographerNCategoriesRepository;
import com.ssafy.core.repository.photographer.PhotographerNPlacesRepository;
import com.ssafy.core.repository.photographer.PhotographerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
@RequiredArgsConstructor
public class PhotographerService {
    private final ReviewRepository reviewRepository;
    private final PhotographerNCategoriesRepository photographerNCategoriesRepository;
    private final PhotographerNPlacesRepository photographerNPlacesRepository;
    private final PhotographerRepository photographerRepository;
    private final UserRepository userRepository;
    private final S3UploaderService s3UploaderService;
    private final PhotographerHeartRepository photographerHeartRepository;
    private final CategoriesRepository categoriesRepository;
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    /**
     * 작가 등록
     *
     * @param multipartFile 프로필 이미지
     * @param photographer
     * @throws USER_NOT_FOUND 유저를 찾을 수 없을 때 에러
     * @throws IOException
     */
    public void addPhotographer(MultipartFile multipartFile, PhotographerReqDto photographer) throws IOException{
        photographer.setPhotographerId(UserService.getLogInUser().getId());
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
            places.add(PhotographerNPlaces.builder()
                    .photographer(Photographer.builder().id(user.getId()).build())
                    .places(Places.builder().id(place.getPlaceId()).build())
                    .build()
            );
        }

        int minPrice = Integer.MAX_VALUE;
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
            minPrice = Math.min(minPrice, category.getPrice());
        }

        Photographer savedPhotographer = Photographer.builder()
                .user(user)
                .profileImg(photographer.getProfileImg())
                .introduction(photographer.getIntroduction())
                .bank(photographer.getBank())
                .account(photographer.getAccount())
                .places(places)
                .categories(categories)
                .minPrice(minPrice)
                .build();

        photographerRepository.save(savedPhotographer);

        // 유저 역할 photographer로 수정
        user.updateRole(Role.PHOTOGRAPHER);
        userRepository.save(user);
    }

    /**
     * 작가 프로필 조회
     *
     * @return 작가 프로필 객체
     * @throws PHOTOGRAPHER_NOT_FOUND 사진작가를 찾을 수 없을 때 에러
     */
    public PhotographerResDto getPhotographer(){
        Long userIdx = UserService.getLogInUser().getId();
        Photographer photographer = photographerRepository.findById(userIdx)
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));
        return new PhotographerResDto().of(photographer);
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
        photographer.setPhotographerId(UserService.getLogInUser().getId());
        Photographer findPhotographer = photographerRepository.findById(photographer.getPhotographerId())
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));

        log.info(photographer.getProfileImg());
        log.info(findPhotographer.getProfileImg());

        if(!photographer.isDeleted()){  // 이미지가 변경되지 않았을 때
            if(file.isEmpty()){ // 이미지가 없을 때
                photographer.setProfileImg(findPhotographer.getProfileImg());
            } else {    // 이미지가 새로 등록되었을 때(기존 이미지 null)
                String fileName = s3UploaderService.upload(file);
                photographer.setProfileImg(fileName);
            }
        } else {
            // 기존 이미지 삭제
            s3UploaderService.deleteFile(findPhotographer.getProfileImg().trim());
            if(!file.isEmpty()) {    // 이미지가 수정되었을 때
                String fileName = s3UploaderService.upload(file);
                photographer.setProfileImg(fileName);
            }
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
        findPhotographer.updateBank(photographer.getBank());
        findPhotographer.updateAccount(photographer.getAccount());
        findPhotographer.updateIntroduction(photographer.getIntroduction());
        findPhotographer.updatePlaces(places);
        findPhotographer.updateCategories(categories);

        return new PhotographerResDto().of(photographerRepository.save(findPhotographer));
    }

    /**
     * 사진 작가 프로필 삭제
     *
     * @throws PHOTOGRAPHER_NOT_FOUND 사진작가를 찾을 수 없을 때 에러
     */
    public void removePhotographer(){
        Long userId = UserService.getLogInUser().getId();
        Photographer findPhotographer = photographerRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));

        // 이미지 삭제
        if(findPhotographer.getProfileImg() != null) {
            s3UploaderService.deleteFile(findPhotographer.getProfileImg().trim());
        }
        photographerRepository.delete(findPhotographer);

        List<Article> articleList = articleRepository.findByUserIdOrderByIdDesc(userId);
        for (Article article : articleList) {
            String photoUriList = article.getPhotoUrls();
            photoUriList = photoUriList.replace("[","").replace("]","");
            List<String> photoUrls = new ArrayList<>(Arrays.asList(photoUriList.split(",")));
            photoUrls.forEach(str -> s3UploaderService.deleteFile(str.trim()));
            articleRepository.delete(article);
        }

        // 일반 유저로 전환
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.updateRole(Role.USER);
        userRepository.save(user);
    }

    /**
     * categoryId로 작가 조회
     *
     * @param categoryName
     * @return List<PhotographerForListDto>
     * @throws CATEGORY_NOT_FOUND 해당 카테고리가 없을 때 에러
     * @throws PHOTOGRAPHER_NOT_FOUND 사진작가를 찾을 수 없을 때 에러
     */
    public List<PhotographerForListDto> getPhotographerListByCategory(String categoryName) {
        Long userId = UserService.getLogInUser().getId();
        List<Long> categoryIdList = categoriesRepository.findAllIdByNameContaining(categoryName);
        if (categoryIdList.isEmpty()) {
            throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        log.info("해당 카테고리 존재");

        List<PhotographerQdslDto> photographerList =
                photographerNCategoriesRepository.findByCategoryId(userId, categoryIdList);
        log.info("카테고리로 작가 조회");

        List<PhotographerForListDto> photographerForList = new ArrayList<>();
        for (PhotographerQdslDto photographerQuerydsl : photographerList) {
            photographerForList.add(new PhotographerForListDto().of(photographerQuerydsl));
        }

        return photographerForList;
    }

    /**
     * 주변 작가 조회
     *
     * @param address
     * @param criteria
     * @return List<PhotographerForListDto>
     */
    public PhotographerNearDto getPhotographerListByAddresss(String address, String criteria) {
        Long userId = UserService.getLogInUser().getId();
        String[] addresssList = address.split(" ");
        List<PhotographerQdslDto> photographerList = photographerNPlacesRepository
                .findPhotographerByAddress(userId, addresssList[0], addresssList[1], criteria);
        log.info("주변 작가 조회");

        List<PhotographerForListDto> photographerForList = new ArrayList<>();
        for (PhotographerQdslDto photographerQuerydsl : photographerList) {
            photographerForList.add(new PhotographerForListDto().of(photographerQuerydsl));
        }

        String photoUrl = null;
        Optional<Photographer> photographer = photographerRepository.findById(userId);
        if (photographer.isPresent()) {     // 조회자가 사진작가면 프로필 이미지 반환
            photoUrl = photographer.get().getProfileImg();
        }

        return new PhotographerNearDto().of(photoUrl, photographerForList);
    }

    /***
     * 사진작가 좋아요 / 좋아요 취소
     *
     * @param photographerId
     * @return 포토그래퍼 id, isHeart boolean
     * @throws PHOTOGRAPHER_NOT_FOUND
     * @throws USER_NOT_FOUND
     */
    public PhotographerHeartDto addHeartPhotographer(Long photographerId){
        Photographer photographer = photographerRepository.findById(photographerId)
                .orElseThrow(()-> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));

        User user = userRepository.findById(photographerId)
                .orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));
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

    /***
     * 내가 좋아요 누른 작가 찾기
     * @return List<PhotographerForListDto> 내가 좋아요 누른 작가리스트
     */
    public List<PhotographerForListDto> getPhotographerListByUser() {
        User user = UserService.getLogInUser();

        List<PhotographerHeart> photographerList = photographerHeartRepository.findByUser(user);

        log.info(photographerList.toString());

        if (photographerList.isEmpty()){
            return new ArrayList<>();
        }

        List<PhotographerForListDto> photographerForList = new ArrayList<>();
        for (PhotographerHeart photographerHeart : photographerList){
            Photographer photographer = photographerHeart.getPhotographer();
            ReviewQdslDto review = reviewRepository.findByPhotographerId(photographer.getId());

            photographerForList.add(new PhotographerForListDto().of(PhotographerQdslDto.builder()
                    .photographer(photographer)
                    .heart(photographerHeartRepository.countByPhotographer(photographer))
                    .hasHeart(true)
                    .avgScore(review.getAvgScore())
                    .reviewCount(review.getReviewCount())
                    .build()));
        }

        return photographerForList;
    }

    /***
     * 포트폴리오의 작가정보
     * @param photographerId 유저 아이디
     * @return 포토그래퍼정보 + 해당 포토그래퍼가 가진 article 게시글 전체조회
     * @throws PHOTOGRAPHER_NOT_FOUND
     * @throws UsernameNotFoundException 유저 없을 때
     */

    @Transactional
    public PhotographerInfoDto getPhotographerInformation(Long photographerId) {
        User logInUser = UserService.getLogInUser();

        Photographer photographer = photographerRepository.findById(photographerId)
                .orElseThrow(()->new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));

        Boolean isMe = articleService.isMe(logInUser, photographer.getUser());
        Boolean isHeart = this.isHearted(logInUser, photographer);
        Long hearts = photographerHeartRepository.countByPhotographer(photographer);

        // 활동지역
        List<String> places = new ArrayList<>();
        for(PhotographerNPlaces place : photographer.getPlaces()){
            places.add(place.getPlaces().getFirst()+" " +place.getPlaces().getSecond());
        }

        // 카테고리
        List<String> categories = new ArrayList<>();
        for(PhotographerNCategories category : photographer.getCategories()){
            categories.add(category.getCategory().getName());
        }

        return new PhotographerInfoDto().of(photographer, isMe, isHeart, hearts, places, categories);
    }

}
