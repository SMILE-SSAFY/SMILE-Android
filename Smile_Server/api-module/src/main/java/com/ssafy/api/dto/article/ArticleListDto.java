package com.ssafy.api.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ArticleListDto {
    /***
     * 작가별 게시글 조회에서 id 하나의 photoUrl만 돌려주기 위한 Dto
     * {@code @author:} 신민철
     */
    private Long id;
    private String photoUrl;
}
