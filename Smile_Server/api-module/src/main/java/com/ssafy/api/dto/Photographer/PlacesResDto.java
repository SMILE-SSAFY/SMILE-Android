package com.ssafy.api.dto.Photographer;

import lombok.Builder;
import lombok.Data;

/**
 * 활동지역 응답 DTO
 *
 * @author 김정은
 */
@Data
@Builder
public class PlacesResDto {
    private String first;
    private String second;
}
