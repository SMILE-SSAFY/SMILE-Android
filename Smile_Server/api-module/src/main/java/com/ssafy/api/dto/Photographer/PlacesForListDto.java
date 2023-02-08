package com.ssafy.api.dto.Photographer;

import lombok.Builder;
import lombok.Data;

/**
 * 작가 검색할 때 사용하는 Dto
 *
 * @author 서재건
 */
@Data
@Builder
public class PlacesForListDto {

    // place의 first와 second를 합친 문자열
    private String place;
}
