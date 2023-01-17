package com.ssafy.api.dto;

import com.ssafy.core.entity.Article;
import lombok.Data;

@Data
public class ArticleDetailDto {
    private Long id;

    private Double latitude;

    private Double longitude;

    private String detailAddress;

//    private LocalDateTime createdAt;

//    @Enumerated(value = EnumType.STRING)
//    private Category category;

    private  String photoUrls;
    //repository를 통해 조회한 객체를 dto로 변환
    public ArticleDetailDto(Article article){
        this.id = article.getId();
        this.latitude = article.getLatitude();
        this.longitude = article.getLongitude();
        this.detailAddress = article.getDetailAddress();
        this.photoUrls = article.getPhotoUrls();
    }
}
