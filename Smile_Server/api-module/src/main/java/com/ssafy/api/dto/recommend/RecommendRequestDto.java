package com.ssafy.api.dto.recommend;

import lombok.Builder;
import lombok.Data;

/**
 * 추천 리퀘스트 Dto
 *
 * @author 이지윤
 */
@Data
@Builder
public class RecommendRequestDto {
 private Long photographerId;
 private String keyword;
 private Boolean heart;
}
