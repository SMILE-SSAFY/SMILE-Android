package com.ssafy.api.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * 좋아요 누른 이후 좋아요 여부 체크
 * @author 신민철
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleHeartDto {
    private Long articleId;
    private Boolean isHeart;

    public ArticleHeartDto of(Long articleId, boolean isHearted){
        return ArticleHeartDto.builder()
                .articleId(articleId)
                .isHeart(isHearted)
                .build();
    }
}
