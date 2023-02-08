package com.ssafy.api.dto.Reservation;

import com.ssafy.core.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/***
 * 리뷰 상세정보 조회
 * @author 신민철
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDetailDto {
    private Long id;

    private String photoUrl;

    private LocalDateTime createdAt;

    private String content;

    private Double score;

    public ReviewDetailDto of(Review review){
        return ReviewDetailDto.builder()
                .id(review.getId())
                .createdAt(review.getCreatedAt())
                .photoUrl(review.getPhotoUrl())
                .content(review.getContent())
                .score(review.getScore())
                .build();
    }
}
