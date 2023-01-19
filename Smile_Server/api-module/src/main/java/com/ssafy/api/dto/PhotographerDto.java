package com.ssafy.api.dto;

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
public class PhotographerDto {
    private Long photographerId;
    private String profileImg;
    private String introduction;
    private String account;
    private int heart;
    private List<Places> places;

    /**
     * Photographer Entity에서 Photographer DTO로 변경
     *
     * @param photographer  PhotographerEntity
     * @return 변환된 DTO
     */
    public PhotographerDto of(Photographer photographer) {
        return PhotographerDto.builder()
                .photographerId(photographer.getId())
                .profileImg(photographer.getProfileImg())
                .introduction(photographer.getIntroduction())
                .account(photographer.getAccount())
                .heart(photographer.getHeart())
                .places(photographer.getPlaces())
                .build();
    }
}
