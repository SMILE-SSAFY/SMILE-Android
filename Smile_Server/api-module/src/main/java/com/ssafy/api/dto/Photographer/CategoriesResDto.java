package com.ssafy.api.dto.Photographer;

import lombok.Builder;
import lombok.Data;

/**
 * 카테고리 응답 DTO
 *
 * @author 김정은
 */
@Data
@Builder
public class CategoriesResDto {
    private Long categoryId;
    private String name;
    private int price;
    private String description;
}
