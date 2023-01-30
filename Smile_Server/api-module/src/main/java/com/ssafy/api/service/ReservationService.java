package com.ssafy.api.service;

import com.ssafy.api.dto.Photographer.PlacesForListDto;
import com.ssafy.api.dto.Reservation.CategoriesInfoResDto;
import com.ssafy.api.dto.Reservation.CategoryDetailDto;
import com.ssafy.api.dto.Reservation.PhotographerInfoDto;
import com.ssafy.api.dto.Reservation.ReservationReqDto;
import com.ssafy.api.dto.Reservation.ReservationResDto;
import com.ssafy.core.dto.CategoriesQdslDto;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.Places;
import com.ssafy.core.entity.Reservation;
import com.ssafy.core.entity.User;
import com.ssafy.core.exception.CustomException;
import com.ssafy.core.exception.ErrorCode;
import com.ssafy.core.repository.PhotographerNCategoriesRepository;
import com.ssafy.core.repository.PhotographerNPlacesRepository;
import com.ssafy.core.repository.PhotographerRepository;
import com.ssafy.core.repository.ReservationRepository;
import com.ssafy.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * author @김정은
 */
@Service
@Slf4j
public class ReservationService {
    @Autowired
    private PhotographerRepository photographerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PhotographerNCategoriesRepository photographerNCategoriesRepository;
    @Autowired
    private PhotographerNPlacesRepository photographerNPlacesRepository;

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
        User user = userRepository.findById(reservation.getPhotographerId()).get();
        return res.of(entity, user.getName(), user.getPhoneNumber());
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
}
