package com.ssafy.api.service;

import com.ssafy.api.dto.Photographer.PlacesForListDto;
import com.ssafy.api.dto.Reservation.CategoriesInfoResDto;
import com.ssafy.api.dto.Reservation.CategoryDetailDto;
import com.ssafy.api.dto.Reservation.PhotographerInfoDto;
import com.ssafy.api.dto.Reservation.ReservationListDto;
import com.ssafy.api.dto.Reservation.ReservationReqDto;
import com.ssafy.api.dto.Reservation.ReservationResDto;
import com.ssafy.api.dto.Reservation.ReservationStatusDto;
import com.ssafy.core.code.ReservationStatus;
import com.ssafy.core.code.Role;
import com.ssafy.core.dto.CategoriesQdslDto;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.Places;
import com.ssafy.core.entity.Reservation;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.UserRepository;
import com.ssafy.core.repository.photographer.PhotographerNCategoriesRepository;
import com.ssafy.core.repository.photographer.PhotographerNPlacesRepository;
import com.ssafy.core.repository.photographer.PhotographerRepository;
import com.ssafy.core.repository.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void changeStatus(ReservationStatusDto statusDto){
        Reservation reservation = reservationRepository.findById(statusDto.getReservationId())
                .orElseThrow(() -> new CustomException(ErrorCode.RESERVATION_NOT_FOUND));

        // 해당하는 유저가 아닐 경우
        if(reservation.getUser().getId() != statusDto.getUserId()
                || reservation.getPhotographer().getId() != statusDto.getUserId()){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        if(reservation.getStatus() == ReservationStatus.예약취소){
            throw new CustomException(ErrorCode.ALREADY_CANCELED);
        }

        reservation.updateStatus(statusDto.getStatus());
        reservationRepository.save(reservation);
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

        List<Reservation> reservationList = reservationRepository.findReservationsByPhotographerId(user.getId());
        if (reservationList.isEmpty()) {
            throw new CustomException(ErrorCode.RESERVATION_NOT_FOUND);
        }
        log.info("작가 예약 목록 조회");

        List<ReservationListDto> reservationPhotographerList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            ReservationListDto reservationPhotographer = new ReservationListDto();
            User reservationUser = reservation.getUser();
            reservationPhotographerList.add(
                    reservationPhotographer.of(reservation, reservationUser.getName(), reservationUser.getPhoneNumber())
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
        log.info("유저 예약 목록 조회 시작");
        List<Reservation> reservationList = reservationRepository.findReservationsByUserId(userId);
        if (reservationList.isEmpty()) {
            throw new CustomException(ErrorCode.RESERVATION_NOT_FOUND);
        }
        log.info("유저 예약 목록 조회");

        List<ReservationListDto> reservationPhotographerList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            ReservationListDto reservationPhotographer = new ReservationListDto();
            User reservationUser = reservation.getPhotographer().getUser();
            reservationPhotographerList.add(
                    reservationPhotographer.of(reservation, reservationUser.getName(), reservationUser.getPhoneNumber())
            );
        }
        return reservationPhotographerList;
    }
}
