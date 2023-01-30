package com.ssafy.api.controller;

import com.ssafy.api.dto.Reservation.ReservationReqDto;
import com.ssafy.api.service.ReservationService;
import com.ssafy.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
     * @return 정상일 때 OK
     */
    @PostMapping
    public ResponseEntity<?> registerReservation(@RequestBody ReservationReqDto reservation){
        log.info(reservation.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        reservation.setUserId(user.getId());

        return ResponseEntity.ok(reservationService.reserve(reservation));
    }

    @GetMapping("/{photographerId}")
    public ResponseEntity<?> getPhotographerInfo(@PathVariable("photographerId") Long photographerId){
        return ResponseEntity.ok(reservationService.getPhotographerInfo(photographerId));
    }

}
