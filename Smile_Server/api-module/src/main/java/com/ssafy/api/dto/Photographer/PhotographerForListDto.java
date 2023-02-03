package com.ssafy.api.dto.Photographer;

import com.ssafy.core.dto.PhotographerQdslDto;
import com.ssafy.core.entity.PhotographerNCategories;
import com.ssafy.core.entity.PhotographerNPlaces;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 작가 검색할 때 사용하는 Dto
 *
 * @author 서재건
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotographerForListDto {
    private Long photographerId;
    private String name;
    private String profileImg;
    private List<PlacesForListDto> places;
    private List<CategoriesForListDto> categories;
    private boolean hasHeart;
    private int heart;
    private double avgScore;
    private int reviewCount;

    /**
     * Photographer Entity에서 검색용 photographer DTO로 변경
     *
     * @param photographerQuerydsl  photographerQuerydslDto
     * @return 변환된 DTO
     */
    public PhotographerForListDto of(PhotographerQdslDto photographerQuerydsl) {
        // 활동지역
        List<PlacesForListDto> places = new ArrayList<>();
        for(PhotographerNPlaces place : photographerQuerydsl.getPhotographer().getPlaces()){
            places.add(PlacesForListDto.builder()
                    .place(place.getPlaces().getFirst() + " " + place.getPlaces().getSecond())
                    .build());
        }
        // 카테고리
        List<CategoriesForListDto> categories = new ArrayList<>();
        for(PhotographerNCategories category : photographerQuerydsl.getPhotographer().getCategories()){
            categories.add(CategoriesForListDto.builder()
                    .name(category.getCategory().getName())
                    .price(category.getPrice())
                    .build());
        }

        return PhotographerForListDto.builder()
                .photographerId(photographerQuerydsl.getPhotographer().getId())
                .name(photographerQuerydsl.getPhotographer().getUser().getName())
                .profileImg(photographerQuerydsl.getPhotographer().getProfileImg())
                .places(places)
                .categories(categories)
                .heart(Math.toIntExact(photographerQuerydsl.getHeart()))
                .hasHeart(photographerQuerydsl.isHasHeart())
                .avgScore(Math.round(photographerQuerydsl.getAvgScore() * 10) / 10.0)
                .reviewCount(Math.toIntExact(photographerQuerydsl.getReviewCount()))
                .build();
    }
}
