package com.ssafy.api.dto.article;

import com.ssafy.core.entity.Article;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
public class ArticlePostTestDto {
    private Double latitude;

    private Double longitude;

    private String detailAddress;

    private String category;

    private List<MultipartFile> imageList;

    public Article toEntity(){
        return Article.builder()
                .latitude(latitude)
                .longitude(longitude)
                .detailAddress(detailAddress)
                .category(category)
                .build();
    }
}
