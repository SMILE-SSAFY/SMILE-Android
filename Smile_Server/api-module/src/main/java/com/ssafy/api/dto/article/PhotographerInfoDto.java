package com.ssafy.api.dto.article;

import com.ssafy.core.entity.Places;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhotographerInfoDto {
    /***
     * 작가별 게시글 리스트를 반환하는 Dto*
     * {@code @Author} : 신민철
     */
    private Long photographerId;
    private Boolean isMe;
//    private Boolean isHeart;
    private String photographerName;
    private String profileImg;
    private String introduction;
    private List<Places> places;
}
