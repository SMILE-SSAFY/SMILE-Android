package com.ssafy.api.service;

import com.querydsl.core.Tuple;
import com.ssafy.api.dto.Reservation.CategoriesInfoResDto;
import com.ssafy.api.dto.Reservation.PhotographerInfoDto;
import com.ssafy.api.dto.Reservation.ReservationDto;
import com.ssafy.core.entity.Categories;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.Reservation;
import com.ssafy.core.entity.User;
import com.ssafy.core.repository.PhotographerNCategoriesRepository;
import com.ssafy.core.repository.PhotographerRepository;
import com.ssafy.core.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 예약 관련 Service
 *
 * author @김정은
 */
@Service
@Slf4j
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PhotographerNCategoriesRepository photographerNCategoriesRepository;

    /**
     * 예약 등록
     *
     * @param reservation
     */
    public void reserve(ReservationDto reservation){

        Reservation savedReservation = Reservation.builder()
                .photographer(Photographer.builder().id(reservation.getPhotographerId()).build())
                .user(User.builder().id(reservation.getUserId()).build())
                .price(reservation.getPrice())
                .email(reservation.getEmail())
                .place(reservation.getAddress() + " " + reservation.getDetailAddress())
                .reservedAt(reservation.getDate())
                .reservedTime(Time.valueOf(reservation.getTime()))
                .createdAt(LocalDateTime.now())
                .build();

        reservationRepository.save(savedReservation);
    }

    public PhotographerInfoDto getPhotographerInfo(Long photographerId){
        // 예약취소된 예약 외 예약 할 수 있는 날
        List<Date> findDates =
                reservationRepository
                        .findReservedAtByPhotographerIdAndReservedAt(photographerId, Date.valueOf(LocalDate.now()));

        List<Tuple> findCategories = photographerNCategoriesRepository.findCategoriesByPhotographerId(photographerId);
//        List<CategoriesInfoResDto> categories = new ArrayList<>();
//        for(Tuple category: findCategories){
//            if(categories.)
//        }
        return PhotographerInfoDto.builder()
                .days(findDates)
                .build();
    }
}
