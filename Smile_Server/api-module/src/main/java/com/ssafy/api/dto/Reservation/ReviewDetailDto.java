package com.ssafy.api.dto.Reservation;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/***
 * 리뷰 상세정보 조회
 * @author 신민철
 */
@Data
@Builder
public class ReviewDetailDto {
    private Long id;

    private String photoUrl;

    private LocalDateTime createdAt;

    private String content;

    private Double score;
}
