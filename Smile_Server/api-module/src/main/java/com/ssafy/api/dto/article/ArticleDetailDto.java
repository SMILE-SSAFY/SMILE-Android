package com.ssafy.api.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ArticleDetailDto {
    /***
     * 게시글 상세정보를 반환하는 Dto
     *
     */
    private Long id;

    private Boolean isME;

    private Boolean isHeart;

    private Long hearts;

    private String detailAddress;

    private LocalDateTime createdAt;

    private String category;

    private  String photoUrls;

}
