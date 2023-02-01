package com.ssafy.api.dto.Reservation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResDto {

    private Long reviewId;

    private Long userId;

    private Integer score;

    private String userName;

    private String content;

    private String photoUrl;
}
