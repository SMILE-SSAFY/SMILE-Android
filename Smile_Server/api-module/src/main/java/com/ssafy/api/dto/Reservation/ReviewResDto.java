package com.ssafy.api.dto.Reservation;

import lombok.Builder;
import lombok.Data;

/***
 * 모든 리뷰조회를 위한 dto
 * @author 신민철
 */
@Data
@Builder
public class ReviewResDto {

    private Long reviewId;

    private Long userId;

    private Integer score;

    private String userName;

    private String content;

    private String photoUrl;

    private Boolean isMe;
}
