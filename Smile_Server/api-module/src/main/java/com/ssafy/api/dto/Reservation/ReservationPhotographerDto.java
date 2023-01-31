package com.ssafy.api.dto.Reservation;

import com.ssafy.core.code.ReservationStatus;
import com.ssafy.core.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Date;
import java.sql.Time;

/**
 * 작가 예약 목록 조회 DTO
 *
 * @author 서재건
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationPhotographerDto {

    @Enumerated(EnumType.ORDINAL)
    private ReservationStatus status;
    private String userName;

    private String phoneNumber;

    private int price;

    private String category;

    private Date date;

    private Time time;

    private String address;

    private boolean isReviewed;

    /**
     * Entity에서 DTO로 변환
     *
     * @param reservation
     * @param name
     * @param phoneNumber
     * @return  ReservationPhotographerDto
     */
    public ReservationPhotographerDto of(Reservation reservation, String name, String phoneNumber){
        return ReservationPhotographerDto.builder()
                .status(reservation.getStatus())
                .userName(name)
                .phoneNumber(phoneNumber)
                .price(reservation.getPrice())
                .category(reservation.getCategoryName() + " " + reservation.getOptions())
                .date(reservation.getReservedAt())
                .time(reservation.getReservedTime())
                .address(reservation.getPlace())
                .isReviewed(reservation.isReviewed())
                .build();
    }

}
