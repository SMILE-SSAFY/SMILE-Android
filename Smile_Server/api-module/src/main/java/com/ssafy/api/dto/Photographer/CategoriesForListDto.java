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
public class CategoriesForListDto {
    private String name;
    private int price;
}
