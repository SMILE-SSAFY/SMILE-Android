package com.ssafy.api.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/***
 * 작가별 게시글 리스트를 반환하는 Dto
 *
 * @author 신민철
 */
public class PhotographerInfoDto {
    private Long photographerId;
    private Boolean isMe;
    private Boolean isHeart;
    private Long hearts;
    private String photographerName;
    private String profileImg;
    private String introduction;
    private List<String> categories;
    private List<String> places;
    private int minPrice;
}
