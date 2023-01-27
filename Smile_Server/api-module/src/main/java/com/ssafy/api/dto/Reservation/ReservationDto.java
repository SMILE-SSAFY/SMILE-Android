package com.ssafy.api.dto.Reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {
    private Long id;

    private Long photographerId;

    private Long userId;

    private String status;

    private int price;

    private Date date;

    private LocalTime time;

    private boolean isReviewed;
}
