package com.ssafy.api.dto.Photographer;

import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.PhotographerNCategories;
import com.ssafy.core.entity.PhotographerNPlaces;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 사진작가 응답 DTO
 *
 * @author 김정은
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
    private String bank;
    private String account;
    private List<PlacesResDto> places;
    private List<CategoriesResDto> categories;

    /**
     * Photographer Entity에서 Photographer DTO로 변경
     *
     * @param photographer  PhotographerEntity
     * @return 변환된 DTO
     */
    public PhotographerResDto of(Photographer photographer) {
        // 활동지역
        List<PlacesResDto> places = new ArrayList<>();
        for(PhotographerNPlaces place : photographer.getPlaces()){
            places.add(PlacesResDto.builder()
                    .placeId(place.getPlaces().getId())
                    .first(place.getPlaces().getFirst())
                    .second(place.getPlaces().getSecond()).build());
        }
        // 카테고리
        List<CategoriesResDto> categories = new ArrayList<>();
        for(PhotographerNCategories category : photographer.getCategories()){
            categories.add(CategoriesResDto.builder()
                    .categoryId(category.getCategory().getId())
                    .name(category.getCategory().getName())
                    .price(category.getPrice())
                    .description(category.getDescription())
                    .build());
        }

        return PhotographerResDto.builder()
                .photographerId(photographer.getId())
                .name(photographer.getUser().getName())
                .profileImg(photographer.getProfileImg())
                .introduction(photographer.getIntroduction())
                .bank(photographer.getBank())
                .account(photographer.getAccount())
                .places(places)
                .categories(categories)
                .build();
    }
}
