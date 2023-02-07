package com.ssafy.api.dto.Photographer;

import lombok.Builder;
import lombok.Data;

/**
 * 카테고리 요청 DTO
 *
 * @author 김정은
 */
@Data
@Builder
public class CategoriesReqDto {
    private long categoryId;
    private int price;
    private String description;
}
