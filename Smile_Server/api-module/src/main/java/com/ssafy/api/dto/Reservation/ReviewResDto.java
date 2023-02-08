package com.ssafy.api.dto.Reservation;

import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/***
 * 모든 리뷰조회를 위한 dto
 * @author 신민철
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResDto {

    private Long reviewId;

    private Long userId;

    private Double score;

    private String userName;

    private String content;

    private String photoUrl;

    private LocalDateTime createdAt;

    private Boolean isMe;

    public ReviewResDto of (Review review, Boolean isMe){
        return ReviewResDto.builder()
                .reviewId(review.getId())
                .userId(review.getUser().getId())
                .isMe(isMe)
                .userName(review.getUser().getName())
                .score(review.getScore())
                .content(review.getContent())
                .photoUrl(review.getPhotoUrl())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
