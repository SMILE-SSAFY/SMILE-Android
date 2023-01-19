package com.ssafy.api.dto;

import com.ssafy.core.entity.Article;
import com.ssafy.core.entity.Categories;
import lombok.Data;

import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Data
public class ArticleDetailDto {
    /***
     * 게시글 상세정보를 반환하는 Dto
     *
     */
    private Long id;

    private Double latitude;

    private Double longitude;

    private String detailAddress;

    private LocalDateTime createdAt;

    private String category;

    private  String photoUrls;
    //repository를 통해 조회한 객체를 dto로 변환
    public ArticleDetailDto(Article article){
        this.id = article.getId();
        this.latitude = article.getLatitude();
        this.longitude = article.getLongitude();
        this.createdAt = article.getCreatedAt();
        this.detailAddress = article.getDetailAddress();
        this.photoUrls = article.getPhotoUrls();
        this.category = article.getCategory();
    }
}
