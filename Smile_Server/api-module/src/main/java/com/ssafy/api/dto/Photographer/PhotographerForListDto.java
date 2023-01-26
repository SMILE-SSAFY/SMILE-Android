package com.ssafy.api.dto.Photographer;

import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.PhotographerNCategories;
import com.ssafy.core.entity.PhotographerNPlaces;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * 작가 검색할 때 사용하는 Dto
 *
 * author @서재건
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
    @Value("false")
    private boolean hasHeart;
    private int heart;

    /**
     * Photographer Entity에서 검색용 photographer DTO로 변경
     *
     * @param photographer  PhotographerEntity
     * @return 변환된 DTO
     */
    public PhotographerForListDto of(Photographer photographer) {
        // 활동지역
        List<PlacesForListDto> places = new ArrayList<>();
        for(PhotographerNPlaces place : photographer.getPlaces()){
            places.add(PlacesForListDto.builder()
                    .place(place.getPlaces().getFirst() + " " + place.getPlaces().getSecond())
                    .build());
        }
        // 카테고리
        List<CategoriesForListDto> categories = new ArrayList<>();
        for(PhotographerNCategories category : photographer.getCategories()){
            categories.add(CategoriesForListDto.builder()
                    .name(category.getCategory().getName())
                    .price(category.getPrice())
                    .build());
        }

        return PhotographerForListDto.builder()
                .photographerId(photographer.getId())
                .name(photographer.getUser().getName())
                .profileImg(photographer.getProfileImg())
                .places(places)
                .categories(categories)
                .hasHeart(false)
                .heart(0)
                .build();
    }
}
