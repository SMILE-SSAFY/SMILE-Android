package com.ssafy.api.dto.Photographer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 주변 작가 조회 Dto
 *
 * @author 서재건
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotographerNearDto {

    private String photoUrl;
    private List<PhotographerForListDto> photographer;

    /**
     * 조회자가 작가면 photoUrl 주고 유저면 null값 반환
     *
     * @param photoUrl
     * @param photographer
     * @return
     */
    public PhotographerNearDto of(String photoUrl, List<PhotographerForListDto> photographer) {
        return PhotographerNearDto.builder()
                .photoUrl(photoUrl)
                .photographer(photographer)
                .build();
    }
}