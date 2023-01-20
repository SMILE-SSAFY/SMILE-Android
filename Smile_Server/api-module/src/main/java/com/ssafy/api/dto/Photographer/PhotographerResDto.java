package com.ssafy.api.dto.Photographer;

import com.ssafy.core.entity.Categories;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.Places;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 사진작가 DTO
 *
 * author @김정은
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotographerResDto {
    private Long photographerId;
    private String name;
    private String profileImg;
    private String introduction;
    private String account;
    private int heart;
    private List<Places> places;
    private List<Categories> categories;

    /**
     * Photographer Entity에서 Photographer DTO로 변경
     *
     * @param photographer  PhotographerEntity
     * @return 변환된 DTO
     */
    public PhotographerResDto of(Photographer photographer) {
        return PhotographerResDto.builder()
                .photographerId(photographer.getId())
                .name(photographer.getUser().getName())
                .profileImg(photographer.getProfileImg())
                .introduction(photographer.getIntroduction())
                .account(photographer.getAccount())
                .heart(photographer.getHeart())
                .places(photographer.getPlaces())
                .categories(photographer.getCategories())
                .build();
    }
}
