package com.ssafy.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * 게시글 검색결과 반환 Dto
 *
 * @author 신민철
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSearchDto {
    private Long articleId;

    private Long photographerId;

    private String photographerName;

    private Double latitude;

    private Double longitude;

    private Boolean isHeart;

    private Long hearts;

    private String detailAddress;

    private LocalDateTime createdAt;

    private String category;

    private String photoUrl;

    public ArticleSearchDto of (ArticleQdslDto articleQuerydsl){

        String photoUrls = articleQuerydsl.getArticle().getPhotoUrls().replace("[", "").replace("]", "");
        List<String> photoUrlList = new ArrayList<>(Arrays.asList(photoUrls.split(",")));

        return ArticleSearchDto.builder()
                .articleId(articleQuerydsl.getArticle().getId())
                .photographerId(articleQuerydsl.getArticle().getUser().getId())
                .photographerName(articleQuerydsl.getArticle().getUser().getName())
                .latitude(articleQuerydsl.getArticle().getLatitude())
                .longitude(articleQuerydsl.getArticle().getLongitude())
                .detailAddress(articleQuerydsl.getArticle().getDetailAddress())
                .isHeart(articleQuerydsl.isHeart())
                .hearts(articleQuerydsl.getHearts())
                .createdAt(articleQuerydsl.getArticle().getCreatedAt())
                .category(articleQuerydsl.getArticle().getCategory())
                .photoUrl(photoUrlList.get(0).trim())
                .build();
    }
}
