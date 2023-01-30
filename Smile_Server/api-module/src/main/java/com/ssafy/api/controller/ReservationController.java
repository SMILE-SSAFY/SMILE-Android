package com.ssafy.api.controller;

import com.ssafy.api.dto.Reservation.ReservationReqDto;
import com.ssafy.api.service.ReservationService;
import com.ssafy.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 예약 관련 Controller
 *
 * author @김정은
 */
@RestController
@RequestMapping("/api/reservation")
@Slf4j
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    /**
     * 예약 등록하기
     *
     * @param reservation
     * @return ReservationReqDto
     */
    @PostMapping
    public ResponseEntity<?> registerReservation(@RequestBody ReservationReqDto reservation){
        log.info(reservation.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        reservation.setUserId(user.getId());

        return ResponseEntity.ok(reservationService.reserve(reservation));
    }

    /**
     * 예약 시 사진작가 정보(예약된 날짜, 카테고리, 활동지역) 조회
     *
     * @param photographerId
     * @return PhotographerInfoDto
     */
    @GetMapping("/{photographerId}")
    public ResponseEntity<?> getPhotographerInfo(@PathVariable("photographerId") Long photographerId){
        return ResponseEntity.ok(reservationService.getPhotographerInfo(photographerId));
    }

}
