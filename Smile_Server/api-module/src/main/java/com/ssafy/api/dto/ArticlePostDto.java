package com.ssafy.api.dto;


import com.ssafy.core.entity.Article;
import lombok.Data;

@Data
public class ArticlePostDto {

    private Double latitude;

    private Double longitude;

    private String detailAddress;

//    private LocalDateTime createdAt;

    public Article toEntity(){
        Article build = Article.builder()
                .latitude(latitude)
                .longitude(longitude)
                .detailAddress(detailAddress)
                .build();
        return build;
    }
}
