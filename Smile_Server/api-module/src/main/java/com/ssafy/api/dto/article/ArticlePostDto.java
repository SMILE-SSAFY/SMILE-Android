package com.ssafy.api.dto.article;

import com.ssafy.core.entity.Article;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/***
 * 게시글 등록시 사용하는 Dto
 * @author 신민철
 */
@Data
public class ArticlePostDto {
    private Double latitude;

    private Double longitude;

    private String detailAddress;

    private String category;

    private List<MultipartFile> imageList;
}
