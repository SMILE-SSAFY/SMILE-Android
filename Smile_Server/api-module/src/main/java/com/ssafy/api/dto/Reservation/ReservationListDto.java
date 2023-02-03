package com.ssafy.api.dto.Reservation;

import com.ssafy.core.code.ReservationStatus;
import com.ssafy.core.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

/**
 * 예약 목록 조회 DTO
 *
 * @author 서재건
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationListDto {

    private Long reservationId;
    private ReservationStatus status;
    private String name;
    private String phoneNumber;
    private int price;
    private String categoryName;
    private String options;
    private Date date;
    private Time time;
    private String address;
    private boolean isReviewed;
    private Long reviewId;

    /**
     * Entity에서 DTO로 변환
     *
     * @param reservation
     * @param name
     * @param phoneNumber
     * @return  ReservationPhotographerDto
     */
    public ReservationListDto of(Reservation reservation, String name, String phoneNumber, Long reviewId, Boolean isReviewed){
        return ReservationListDto.builder()
                .reservationId(reservation.getId())
                .status(reservation.getStatus())
                .name(name)
                .phoneNumber(phoneNumber)
                .price(reservation.getPrice())
                .categoryName(reservation.getCategoryName())
                .options(reservation.getOptions())
                .date(reservation.getReservedAt())
                .time(reservation.getReservedTime())
                .address(reservation.getPlace())
                .isReviewed(isReviewed)
                .reviewId(reviewId)
                .build();
    }

}
