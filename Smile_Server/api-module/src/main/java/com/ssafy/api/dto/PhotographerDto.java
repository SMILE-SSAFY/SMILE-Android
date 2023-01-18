package com.ssafy.api.dto;

import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.Places;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotographerDto {
    private Long photographerIdx;
    private String profileImg;
    private String introduction;
    private String account;
    private int heart;
    private List<Places> places;

    public PhotographerDto of(Photographer photographer) {
        return PhotographerDto.builder()
                .photographerIdx(photographer.getId())
                .profileImg(photographer.getProfileImg())
                .introduction(photographer.getIntroduction())
                .account(photographer.getAccount())
                .heart(photographer.getHeart())
                .places(photographer.getPlaces())
                .build();
    }
}
