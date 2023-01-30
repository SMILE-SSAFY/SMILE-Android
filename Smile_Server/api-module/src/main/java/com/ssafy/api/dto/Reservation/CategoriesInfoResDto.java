package com.ssafy.api.dto.Reservation;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 카테고리 정보
 *
 * @author 김정은
 */
@Data
@Builder
public class CategoriesInfoResDto {
    private Long categoryId;
    private String categoryName;
    private List<CategoryDetailDto> details;
}
