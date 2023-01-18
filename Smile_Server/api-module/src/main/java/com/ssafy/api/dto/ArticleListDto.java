package com.ssafy.api.dto;

import com.ssafy.core.entity.Article;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.Places;
import com.ssafy.core.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class ArticleListDto {
    private User user;
    private Long photographerId;
    private String name;
    private String introduction;
    private List<Places> places;
    private List<Article> articles;

    public ArticleListDto(Photographer photographer){
        this.photographerId = photographer.getId();
        this.name = user.getEmail();
        this.introduction = photographer.getIntroduction();
        this.places = photographer.getPlaces();
    }
}
