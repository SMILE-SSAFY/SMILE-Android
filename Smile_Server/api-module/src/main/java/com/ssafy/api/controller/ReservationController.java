package com.ssafy.api.controller;

import com.ssafy.api.dto.Reservation.ReservationListDto;
import com.ssafy.api.dto.Reservation.ReservationReqDto;
import com.ssafy.api.dto.Reservation.ReservationStatusDto;
import com.ssafy.api.dto.Reservation.ReviewPostDto;
import com.ssafy.api.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * 예약 관련 Controller
 *
 * @author 김정은
 * @author 서재건
 * @author 신민철
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
    public ResponseEntity<?> registerReservation(@RequestBody ReservationReqDto reservation) throws IOException {
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
    public ResponseEntity<?> changeReservationStatus(
            @PathVariable("reservationId") Long reservationId, @RequestBody ReservationStatusDto status) throws IOException {
        status.setReservationId(reservationId);
        reservationService.changeStatus(status);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * 작가 예약 목록 조회
     *
     * @return List<ReservationListDto>
     */
    @GetMapping("/photographer")
    public ResponseEntity<List<ReservationListDto>> getPhotographerReservationList() {
        return ResponseEntity.ok().body(reservationService.findPhotographerReservation());
    }

    /**
     * 유저 예약 목록 조회
     *
     * @return List<ReservationListDto>
     */
    @GetMapping("/user")
    public ResponseEntity<List<ReservationListDto>> getUserReservationList() {
        return ResponseEntity.ok().body(reservationService.findUserReservation());
    }

    /***
     * 리뷰 등록
     * @param reservationId 예약 id
     * @param reviewPostDto 리뷰 dto
     * @return HttpStatus.CREATED
     * @throws IOException 파일이 없을 때 에러
     */
    @PostMapping("/review/{reservationId}")
    public ResponseEntity<HttpStatus> addReview(
            @PathVariable("reservationId") Long reservationId, ReviewPostDto reviewPostDto) throws Exception {
        reservationService.addReview(reservationId, reviewPostDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    /***
     * 리뷰 디테일 조회
     *
     * @param reviewId
     * @return 리뷰 디테일
     */
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<?> getReviewDetail(@PathVariable Long reviewId){
        return new ResponseEntity<>(reservationService.reviewDetail(reviewId), HttpStatus.OK);
    }


    /***
     * 해당 작가의 리뷰 리스트 조회
     *
     * @param photographerId 사진작가 인덱스
     * @return List<reviewResDto>
     */
    @GetMapping("/review/list/{photographerId}")
    public ResponseEntity<?> getPhotographerReviewList(@PathVariable("photographerId") Long photographerId){
        return new ResponseEntity<>(reservationService.showReviewList(photographerId), HttpStatus.OK);
    }

    /***
     * 리뷰아이디를 통해 리뷰삭제
     * @param reviewId 리뷰아이디
     * @return HttpStatus.OK
     */
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<HttpStatus> removeReview(@PathVariable("reviewId") Long reviewId){
        reservationService.deleteReview(reviewId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * 예약 취소
     *
     * @param reservationId
     * @return HttpStatus.OK
     */
    @PutMapping("/cancel/{reservationId}")
    public ResponseEntity<HttpStatus> cancelReservation(
            @PathVariable("reservationId") Long reservationId) throws IOException {
        reservationService.changeCancelStatus(reservationId);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
