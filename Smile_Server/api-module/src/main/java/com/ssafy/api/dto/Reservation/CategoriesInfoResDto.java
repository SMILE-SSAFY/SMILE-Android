package com.ssafy.api.dto.Reservation;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoriesInfoResDto {
    private Long categoryId;
    private String categoryName;
    private List<CategoryDetailDto> details;
}
