package com.ssafy.api.dto.article;

import com.ssafy.core.entity.Photographer;
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

    public PhotographerInfoDto of(Photographer photographer, Boolean isMe, Boolean isHeart, Long hearts, List<String> places, List<String> categories){
        return PhotographerInfoDto.builder()
                .photographerId(photographer.getId())
                .isMe(isMe)
                .isHeart(isHeart)
                .hearts(hearts)
                .photographerName(photographer.getUser().getName())
                .profileImg(photographer.getProfileImg())
                .introduction(photographer.getIntroduction())
                .categories(categories)
                .places(places)
                .minPrice(photographer.getMinPrice())
                .build();
    }
}
