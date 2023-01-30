package com.ssafy.core.dto;

import com.ssafy.core.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * querydsl에서 게시글 검색할 때 사용하는 dto
 *
 * author @서재건
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleQuerydslDto {

    private Article article;
    private Long hearts;
    private boolean isHeart;
}
