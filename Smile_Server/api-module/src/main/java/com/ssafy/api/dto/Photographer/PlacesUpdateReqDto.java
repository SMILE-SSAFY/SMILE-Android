package com.ssafy.api.dto.Photographer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlacesUpdateReqDto {
    // 연관관계 테이블 인덱스
    private long relationId;
    private long placeId;
}
