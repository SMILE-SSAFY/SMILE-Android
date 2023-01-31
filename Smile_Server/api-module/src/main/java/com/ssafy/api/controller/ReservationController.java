package com.ssafy.api.controller;

import com.ssafy.api.dto.Reservation.ReservationPhotographerDto;
import com.ssafy.api.dto.Reservation.ReservationReqDto;
import com.ssafy.api.dto.Reservation.ReservationStatusDto;
import com.ssafy.api.service.ReservationService;
import com.ssafy.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 예약 관련 Controller
 *
 * @author 김정은
 * @author 서재건
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

    /**
     * 예약 상태 변경
     * * 취소나 완료인 상태에서는 변경 불가
     *
     * @param reservationId
     * @param status
     * @return 정상일 때 OK
     */
    @PutMapping("/status/{reservationId}")
    public ResponseEntity<?> changeStatus(@PathVariable("reservationId") Long reservationId, @RequestBody ReservationStatusDto status){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();

        status.setReservationId(reservationId);
        status.setUserId(user.getId());
        reservationService.changeStatus(status);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/photographer")
    public ResponseEntity<List<ReservationPhotographerDto>> findPhotographerReservation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return ResponseEntity.ok().body(reservationService.findPhotographerReservation(user));
    }
}
