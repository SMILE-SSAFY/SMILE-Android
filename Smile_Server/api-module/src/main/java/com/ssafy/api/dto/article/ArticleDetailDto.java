package com.ssafy.api.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
/***
 * 게시글 상세정보를 반환하는 Dto
 *
 * @author 신민철
 */
@Data
@Builder
@AllArgsConstructor
public class ArticleDetailDto {

    private Long id;

    private Boolean isMe;

    private Boolean isHeart;

    private Long hearts;

    private String detailAddress;

    private LocalDateTime createdAt;

    private String category;

    private String photoUrls;

    private String photographerName;

}
