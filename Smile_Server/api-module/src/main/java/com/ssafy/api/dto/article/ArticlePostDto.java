package com.ssafy.api.dto.article;


import com.ssafy.core.entity.Article;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticlePostDto {
    /***
     * 게시글 등록을 위한 Dto
     * {@code @author:} 신민철
     */

    private Double latitude;

    private Double longitude;

    private String detailAddress;

    private String category;

    public Article toEntity(){
        return Article.builder()
                .latitude(latitude)
                .longitude(longitude)
                .detailAddress(detailAddress)
                .category(category)
                .build();
    }
}
