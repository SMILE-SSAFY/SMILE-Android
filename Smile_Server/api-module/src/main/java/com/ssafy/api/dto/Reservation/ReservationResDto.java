package com.ssafy.api.dto.Reservation;

import com.ssafy.core.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResDto {
    private String photographerName;

    private String phoneNumber;

    private int price;

    private String categoryName;

    private String options;

    private String email;

    private Date date;

    private Time time;

    private String address;

    private boolean isReviewed;

    public ReservationResDto of(Reservation reservation, String name, String phoneNumber){
        return ReservationResDto.builder()
                .photographerName(name)
                .phoneNumber(phoneNumber)
                .price(reservation.getPrice())
                .categoryName(reservation.getCategoryName())
                .options(reservation.getOptions())
                .email(reservation.getEmail())
                .date(reservation.getReservedAt())
                .time(reservation.getReservedTime())
                .address(reservation.getPlace())
                .isReviewed(reservation.isReviewed())
                .build();
    }

}
