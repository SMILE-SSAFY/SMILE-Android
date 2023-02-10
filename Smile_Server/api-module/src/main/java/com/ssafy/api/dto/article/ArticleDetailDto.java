package com.ssafy.api.dto.article;

import com.ssafy.core.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
/***
 * 게시글 상세정보를 반환하는 Dto
 *
 * @author 신민철
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailDto {

    private Long id;

    private Boolean isMe;

    private Boolean isHeart;

    private String profileImg;

    private Long hearts;

    private String detailAddress;

    private LocalDateTime createdAt;

    private String category;

    private String photoUrls;

    private String photographerName;

    public ArticleDetailDto of(Article article, Boolean isMe, Boolean isHeart, Long hearts){
        return ArticleDetailDto.builder()
                .id(article.getId())
                .isMe(isMe)
                .isHeart(isHeart)
                .detailAddress(article.getDetailAddress())
                .category(article.getCategory())
                .createdAt(article.getCreatedAt())
                .photoUrls(article.getPhotoUrls())
                .hearts(hearts)
                .photographerName(article.getUser().getName())
                .build();
    }

}
