package com.ssafy.api.dto.Photographer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoriesUpdateReqDto {
    // 연관관계 테이블 인덱스
    private long relationId;
    private long categoryId;
    private int price;
    private String description;
}
