package com.ssafy.api.service;


import com.ssafy.api.dto.Photographer.PhotographerForListDto;
import com.ssafy.api.dto.recommend.RecommendRequestDto;
import com.ssafy.api.dto.recommend.RecommendResponseDto;
import com.ssafy.core.dto.PhotographerIdQdslDto;
import com.ssafy.core.dto.PhotographerQdslDto;
import com.ssafy.core.dto.ReviewQdslDto;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.PhotographerHeart;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.ReviewRepository;
import com.ssafy.core.repository.photographer.PhotographerHeartRepository;
import com.ssafy.core.repository.photographer.PhotographerNPlacesRepository;
import com.ssafy.core.repository.photographer.PhotographerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 추천 관련 Service
 *
 * @author 이지윤
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendService {

    private final PhotographerRepository photographerRepository;
    private final PhotographerHeartRepository photographerHeartRepository;
    private final PhotographerNPlacesRepository photographerNPlacesRepository;
    private final ReviewRepository reviewRepository;


    /**
     * 유저의 위치정보와 좋아요 정보를 기반으로 작가를 추천한다.
     *
     * @param address
     * @return 위치정보와 좋아요 정보로 유사도 알고리즘을 통해 추천할 작가 정보를 조회해 작가 정보 리스트 리턴
     */
    @Transactional
    public RecommendResponseDto recommendPhotographerByAddress(String address){
        User user = UserService.getLogInUser();

        List<PhotographerHeart> heartPhotographerIdList = photographerHeartRepository.findByUser(user);           // 유저가 좋아요 누른 작가 정보 리스트

        if (heartPhotographerIdList.isEmpty()) {    // 유저가 좋아요 누른 작가가 없는 경우, 프론트에 리턴.
            return RecommendResponseDto.builder()
                    .isHeartEmpty(false)
                    .photographerInfoList(new ArrayList<>())
                    .build();
        }

        List<PhotographerIdQdslDto> dataPhotographerIdList = this.getPhotographerIdListByAddress(address);                                      // 유저의 위치 근처에 존재하는 작가 정보 리스트

        List<RecommendRequestDto> recommendRequestDtoList = this.getReviewByPhotographerId(heartPhotographerIdList, dataPhotographerIdList);    // Http API requestDto 생성

        Integer[] recommendResponseDto = this.getFlaskRecommendResponse(recommendRequestDtoList);                                               // Http API response (=추천 작가 Id 정보 리스트)

        return this.getPhotographerListByPhotographerId(recommendResponseDto);                                                                  // 추천 작가 정보 리스트
    }

    /**
     * 유저의 위치 근처에 존재하는 작가들을 조회한다.
     *
     * @param address 유저의 위치 정보
     * @return List<PhotographerIdQdslDto> 위치 정보로 가장 가까운 거리부터 전국까지 거리를 확장하면서 조회한 작가 정보 리스트
     */
    private List<PhotographerIdQdslDto> getPhotographerIdListByAddress(String address){

        String[] addressList = address.split(" ");

        List<PhotographerIdQdslDto> result = photographerNPlacesRepository.findPhotographerIdByAddress(addressList);

        while (result.isEmpty()){
            addressList = Arrays.copyOf(addressList, addressList.length-1);
            result = photographerNPlacesRepository.findPhotographerIdByAddress(addressList);
        }
        return result;
    }


    /**
     * Flask 서버에 작가 추천 API를 요청하기 위해 넘겨줄 RequestDto를 생성한다.
     *
     * @param heartPhotographerList 유저가 좋아요를 누른 작가 정보 리스트
     * @param dataPhotographerList 유저의 위치 근처 내 존재하는 작가 정보 리스트
     * @return List<RecommendRequestDto> 작가 추천 API 요청을 위한 requestDto
     */
    private List<RecommendRequestDto> getReviewByPhotographerId(
            List<PhotographerHeart> heartPhotographerList, List<PhotographerIdQdslDto> dataPhotographerList){
        List<RecommendRequestDto> result = new ArrayList<>();

        for (PhotographerHeart photographerDto : heartPhotographerList){
            Long photographerId = photographerDto.getPhotographer().getId();
            reviewRepository.findAllByPhotographerId(photographerId).forEach(review -> {
                result.add(RecommendRequestDto.builder()
                        .photographerId(photographerId).keyword(review.getKeywords()).heart(true).build());
            });
        }

        for (PhotographerIdQdslDto photographerDto : dataPhotographerList){
            Long photographerId = photographerDto.getPhotographerId();
            reviewRepository.findAllByPhotographerId(photographerId).forEach(review -> {
                result.add(RecommendRequestDto.builder()
                        .photographerId(photographerId).keyword(review.getKeywords()).heart(false).build());
            });
        }

        return result;
    }


    /**
     *
     * Flask 서버에 HTTP Request 요청 후, response를 받는다.
     *
     * @param requestDtoList 작가 추천 API 요청을 위한 requestDto
     * @return ResponseEntity<String> Response 엔티티
     */
    private Integer[] getFlaskRecommendResponse(List<RecommendRequestDto> requestDtoList) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        HttpEntity<List<RecommendRequestDto>> recommendRequestList = new HttpEntity<>(requestDtoList);

        return restTemplate.postForObject(
                "http://i8d102.p.ssafy.io:5000/recommend",
                recommendRequestList,
                Integer[].class
        );
    }


    /***
     * 추천 받은 작가 정보 조회
     *
     * @param photographerIdList 추천 받은 작가 Id 리스트
     * @return List<PhotographerForListDto> 추천 받은 작가 정보 리스트
     */
    private RecommendResponseDto getPhotographerListByPhotographerId(Integer[] photographerIdList) {

        log.info(Arrays.toString(photographerIdList));
        List<PhotographerForListDto> result = new ArrayList<>();

        for (Integer photographerId : photographerIdList){

            Long id = Long.valueOf(photographerId);

            Photographer photographer = photographerRepository.findById(id).orElseThrow(()->
                new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND)
            );

            ReviewQdslDto review = reviewRepository.findByPhotographerId(id);

            PhotographerQdslDto photographerQdslDto = PhotographerQdslDto.builder()
                    .photographer(photographer)
                    .heart(photographerHeartRepository.countByPhotographer(photographer))
                    .hasHeart(false)
                    .avgScore(review.getAvgScore())
                    .reviewCount(review.getReviewCount())
                    .build();

            result.add(new PhotographerForListDto().of(photographerQdslDto));
        }

        return RecommendResponseDto.builder()
                .isHeartEmpty(false)
                .photographerInfoList(result)
                .build();
    }


}
