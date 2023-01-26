package com.ssafy.api.dto.Photographer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 활동지역 요청 DTO
 *
 * author @김정은
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlacesReqDto {
    private String placeId;
}
