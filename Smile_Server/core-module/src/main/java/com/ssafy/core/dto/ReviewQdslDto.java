package com.ssafy.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * querydsl 에서 평점과 리뷰 갯수 받기 위한 dto
 *
 * @author 서재건
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewQdslDto {
    private double score;
    private Long reviews;
}
