package com.ssafy.api.controller;

import com.ssafy.api.dto.Reservation.ReservationDto;
import com.ssafy.api.service.ReservationService;
import com.ssafy.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation")
@Slf4j
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> registerReservation(@RequestBody ReservationDto reservation){
        log.info(reservation.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        reservation.setUserId(user.getId());

        reservationService.reserve(reservation);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
