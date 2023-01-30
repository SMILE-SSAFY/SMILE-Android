package com.ssafy.api.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/***
 * 게시글 검색결과 반환 Dto
 *
 * @author 신민철
 */
@Data
@Builder
@AllArgsConstructor
public class ArticleSearchDto {
    private Long articleId;

    private String photographerName;

    private Double latitude;

    private Double longitude;

    private Boolean isHeart;

    private Long hearts;

    private String detailAddress;

    private LocalDateTime createdAt;

    private String category;

    private  String photoUrl;
}
