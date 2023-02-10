package com.ssafy.api.dto.Reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalTime;

/**
 * 예약 요청 DTO
 *
 * @author 김정은
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationReqDto {
    private Long photographerId;

    private Long userId;

    private String receiptId;

    private int price;

    private String categoryName;

    private String options;

    private String email;

    private Date date;

    private LocalTime time;

    // 장소
    private String address;

    // 상세주소
    private String detailAddress;

}
