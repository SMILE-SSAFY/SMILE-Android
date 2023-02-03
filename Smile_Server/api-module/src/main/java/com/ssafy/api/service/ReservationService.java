package com.ssafy.api.service;

import com.ssafy.api.dto.Photographer.PlacesForListDto;
import com.ssafy.api.dto.Reservation.*;
import com.ssafy.core.code.ReservationStatus;
import com.ssafy.core.code.Role;
import com.ssafy.core.dto.CategoriesQdslDto;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.Places;
import com.ssafy.core.entity.Reservation;
import com.ssafy.core.entity.Review;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.ReviewRepository;
import com.ssafy.core.repository.UserRepository;
import com.ssafy.core.repository.photographer.PhotographerNCategoriesRepository;
import com.ssafy.core.repository.photographer.PhotographerNPlacesRepository;
import com.ssafy.core.repository.photographer.PhotographerRepository;
import com.ssafy.core.repository.reservation.ReservationRepository;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.model.request.Cancel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 예약 관련 Service
 *
 * @author 김정은
 * @author 서재건
 * @author 신민철
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {
    private final PhotographerRepository photographerRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final PhotographerNCategoriesRepository photographerNCategoriesRepository;
    private final PhotographerNPlacesRepository photographerNPlacesRepository;
    private final S3UploaderService s3UploaderService;
    private final ReviewRepository reviewRepository;
    private final NotificationService notificationService;

    @Value("${pay.rest-api}")
    private String restApiKey;

    @Value("${pay.private-key}")
    private String privateKey;

    /**
     * 예약 등록
     *
     * @param reservation
     */
    public ReservationResDto reserve(ReservationReqDto reservation){
        if(!photographerRepository.existsById(reservation.getPhotographerId())){
            throw new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND);
        }

        //FIX: 같은 날짜에 같은 사진작가에게 저장하려할 때 에러 발생

        Reservation savedReservation = Reservation.builder()
                .photographer(Photographer.builder().id(reservation.getPhotographerId()).build())
                .user(User.builder().id(reservation.getUserId()).build())
                .receiptId(reservation.getReceiptId())
                .price(reservation.getPrice())
                .categoryName(reservation.getCategoryName())
                .options(reservation.getOptions())
                .email(reservation.getEmail())
                .place(reservation.getAddress() + " " + reservation.getDetailAddress())
                .reservedAt(reservation.getDate())
                .reservedTime(Time.valueOf(reservation.getTime()))
                .createdAt(LocalDateTime.now())
                .build();

        Reservation entity = reservationRepository.save(savedReservation);

        ReservationResDto res = new ReservationResDto();
        User photographer = userRepository.findById(reservation.getPhotographerId()).get();
        return res.of(entity, photographer.getName(), photographer.getPhoneNumber());
    }

    /**
     * 예약 시 사진작가의 정보(예약된 날짜, 카테고리, 활동지역) 조회
     *
     * @param photographerId
     * @return PhotographerInfoDto
     */
    public PhotographerInfoDto getPhotographerInfo(Long photographerId){
        photographerRepository.findById(photographerId)
                .orElseThrow(() -> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));

        // 예약취소된 예약 외 예약 할 수 있는 날
        List<Date> findDates =
                reservationRepository
                        .findReservedAtByPhotographerIdAndReservedAt(photographerId, Date.valueOf(LocalDate.now()));

        // 카테고리
        List<CategoriesQdslDto> findCategories = photographerNCategoriesRepository.findCategoriesByPhotographerId(photographerId);
        Map<Long, CategoriesInfoResDto> categories = new HashMap<>();

        for(CategoriesQdslDto category: findCategories){
            if(categories.containsKey(category.getId())){   // 카테고리 id가 이미 있는 정보일 때
                List<CategoryDetailDto> list = categories.get(category.getId()).getDetails();
                list.add(CategoryDetailDto.builder().price(category.getPrice()).options(category.getDescription()).build());

                categories.get(category.getId()).setDetails(list);
            } else {    // 카테고리가 중복되지 않았을 때
                List<CategoryDetailDto> list = new ArrayList<>(){{
                    add(CategoryDetailDto.builder().price(category.getPrice()).options(category.getDescription()).build());
                }};

                categories.put(category.getId(), CategoriesInfoResDto.builder()
                        .categoryId(category.getId())
                        .categoryName(category.getName())
                        .details(list)
                        .build());
            }
        }
        List<CategoriesInfoResDto> list = new ArrayList<>(categories.values());

        // 장소
        List<PlacesForListDto> places = new ArrayList<>();
        for(Places place : photographerNPlacesRepository.findPlacesByPhotographer(photographerId)){
            PlacesForListDto dto = PlacesForListDto.builder()
                    .place(place.getFirst() + " " + place.getSecond())
                    .build();
            places.add(dto);
        }

        return PhotographerInfoDto.builder()
                .days(findDates)
                .categories(list)
                .places(places)
                .build();
    }

    /**
     * 예약 상태 변경
     *
     * @param statusDto
     */
    @Transactional
    public void changeStatus(ReservationStatusDto statusDto) throws IOException {
        Reservation reservation = reservationRepository.findById(statusDto.getReservationId())
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        // 해당하는 유저가 아닐 경우
        if(reservation.getUser().getId() != statusDto.getUserId()
                && reservation.getPhotographer().getId() != statusDto.getUserId()){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        if(reservation.getStatus() == ReservationStatus.예약취소){
            throw new CustomException(ErrorCode.ALREADY_CANCELED);
        }

        reservation.updateStatus(statusDto.getStatus());
        reservationRepository.save(reservation);

        // FCM 전송
        if(statusDto.getStatus() == ReservationStatus.예약확정){
            notificationService.sendDataMessageTo(NotificationDTO.builder()
                            .requestId(statusDto.getUserId())
                            .registrationToken(reservation.getUser().getFcmToken())
                            .content(reservation.getReservedAt() + "의 예약이 확정되었습니다.")
                    .build());
        }
    }

    /**
     * 작가 예약 목록 조회
     *
     * @param user
     * @return List<ReservationPhotographerDto>
     */
    @Transactional(readOnly = true)
    public List<ReservationListDto> findPhotographerReservation(User user) {
        log.info("작가 예약 목록 조회 시작");
        if (!user.getRole().equals(Role.PHOTOGRAPHER)) {
            throw new CustomException(ErrorCode.FAIL_AUTHORIZATION);
        }
        log.info("Role 작가 확인");

        List<Reservation> reservationList =
                reservationRepository.findByPhotographerIdOrderByReservedAtDescReservedTimeDesc(user.getId());
        log.info("작가 예약 목록 조회");

        List<ReservationListDto> reservationPhotographerList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            ReservationListDto reservationPhotographer = new ReservationListDto();
            User reservationUser = reservation.getUser();
            Review review = reservation.getReview();
            Long reviewId;
            Boolean isReviewed = false;
            if (review == null){
                reviewId = null;
            } else {
                reviewId = review.getId();
                isReviewed = true;
            }
            reservationPhotographerList.add(
                    reservationPhotographer.of(reservation, reservationUser.getName(), reservationUser.getPhoneNumber(), reviewId, isReviewed)
            );
        }
        return reservationPhotographerList;
    }

    /**
     * 유저 예약 목록 조회
     *
     * @param userId
     * @return List<ReservationListDto>
     */
    @Transactional(readOnly = true)
    public List<ReservationListDto> findUserReservation(Long userId) {
        log.info("유저 예약 목록 조회");
        List<Reservation> reservationList =
                reservationRepository.findByUserIdOrderByReservedAtDescReservedTimeDesc(userId);

        List<ReservationListDto> reservationPhotographerList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            ReservationListDto reservationPhotographer = new ReservationListDto();
            User reservationUser = reservation.getPhotographer().getUser();
            Review review = reservation.getReview();
            Long reviewId;
            Boolean isReviewed = false;
            if (review == null){
                reviewId = null;
            } else {
                reviewId = review.getId();
                isReviewed = true;
            }
            reservationPhotographerList.add(
                    reservationPhotographer.of(reservation, reservationUser.getName(), reservationUser.getPhoneNumber(), reviewId, isReviewed)
            );
        }
        return reservationPhotographerList;
    }

    /***
     * 리뷰등록을 위한 서비스
     * @param reservationId 예약아이디
     * @param reviewPostDto 리뷰등록을 위한 dto
     * @throws IOException 파일이 없을 때 생기는 에러
     */

    public void addReview(Long reservationId, ReviewPostDto reviewPostDto) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();

        String fileName = s3UploaderService.upload(reviewPostDto.getImage());

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()->new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        Photographer photographer = reservation.getPhotographer();

        Review review = Review.builder()
                .content(reviewPostDto.getContent())
                .score(reviewPostDto.getScore())
                .PhotoUrl(fileName)
                .user(user)
                .photographer(photographer)
                .reservation(reservation)
                .build();

        reviewRepository.save(review);
    }

    /***
     * 해당 작가에 달린 리뷰를 모두 보여주는 서비스
     * @param photographerId 작가id
     * @return reviewResDto 리뷰리스트
     */
    public List<ReviewResDto> showReviewList(Long photographerId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();

        Photographer photographer = photographerRepository.findById(photographerId).orElseThrow(()-> new CustomException(ErrorCode.PHOTOGRAPHER_NOT_FOUND));

        List<ReviewResDto> reviewResDtoList = new ArrayList<>();

        List<Review> ReviewList = reviewRepository.findByPhotographer(photographer);

        for(Review review : ReviewList){
            boolean isMe = review.getUser() == user;
            ReviewResDto resDto = ReviewResDto.builder()
                    .reviewId(review.getId())
                    .userId(user.getId())
                    .isMe(isMe)
                    .userName(user.getName())
                    .score(review.getScore())
                    .content(review.getContent())
                    .photoUrl(review.getPhotoUrl())
                    .build();
            reviewResDtoList.add(resDto);
        }
        return reviewResDtoList;
    }

    /***
     * 리뷰아이디를 통해 리뷰를 삭제
     * 본인일 경우만 삭제 가능
     * @param reviewId 리뷰아이디
     */
    public void deleteReview(Long reviewId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();

        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if(review.getUser().getId() == user.getId()){
            reviewRepository.deleteById(reviewId);
        }
        throw new CustomException(ErrorCode.USER_MISMATCH);
    }

    /**
     * 예약 취소
     *
     * @param reservationId
     * @param userId
     * @throws IOException
     */
    @Transactional
    public void changeCancelStatus(Long reservationId, Long userId) throws IOException {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));
        log.info("예약 조회 완료");

        // 해당하는 유저가 아닐 경우
        if(reservation.getUser().getId() != userId
                && reservation.getPhotographer().getId() != userId){
            log.info("예약 상태 변경의 권한이 없음");
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        if (!(reservation.getStatus() == ReservationStatus.예약확정전
                || reservation.getStatus() == ReservationStatus.예약확정)) {
            log.info("예약을 취소할 수 없음");
            throw new CustomException(ErrorCode.RESERVATION_NOT_CANCEL);
        }

        String token = "", name = "";
        User user = reservation.getUser();  // 예약한 유저
        User photographer = reservation.getPhotographer().getUser();    // 예약된 사진작가
        if(reservation.getUser().getId() == userId){    // 예약한 유저가 취소한 경우
            token = photographer.getFcmToken();  // 사진작가에게 전달
            name = user.getName();     // 유저이름으로 취소
        } else {    // 사진작가가 취소한 경우
            token = user.getFcmToken();    // 예약한 유저에게 전달
            name = photographer.getName(); // 사진작가 이름으로 취소
        }

        cancelPay(reservation.getReceiptId(), name);

        reservation.updateStatus(ReservationStatus.예약취소);
        log.info("예약 상태 : {}", reservation.getStatus());

        reservationRepository.save(reservation);

        // FCM 전송
        notificationService.sendDataMessageTo(NotificationDTO.builder()
                .requestId(userId)
                .registrationToken(token)
                .content(reservation.getReservedAt() + "의 예약이 확정되었습니다.")
                .build());
    }

    /**
     * 결제취소
     *
     * @param receiptId 결제 영수증 번호
     */
    public void cancelPay(String receiptId, String userName){
        try {
            Bootpay bootpay = new Bootpay(restApiKey, privateKey);
            HashMap<String, Object> token = bootpay.getAccessToken();
            if(token.get("error_code") != null) { //failed
                return;
            }
            Cancel cancel = new Cancel();
            cancel.receiptId = receiptId;
            cancel.cancelUsername = userName;
            cancel.cancelMessage = "사용자 단순 변심";

            HashMap<String, Object> res = bootpay.receiptCancel(cancel);
            if(res.get("error_code") == null) { //success
                log.info("receiptCancel success: " + res);
            } else {
                log.error("receiptCancel false: " + res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 리뷰 조회
     * @param reviewId
     * @return 리뷰 디테일
     */
    public ReviewDetailDto reviewDetail(Long reviewId){
        Review result = reviewRepository.findById(reviewId).orElseThrow(()->new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        return ReviewDetailDto.builder()
                .id(reviewId)
                .photoUrl(result.getPhotoUrl())
                .content(result.getContent())
                .score(result.getScore())
                .build();
    }
}
