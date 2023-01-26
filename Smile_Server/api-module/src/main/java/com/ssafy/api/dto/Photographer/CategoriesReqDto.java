package com.ssafy.api.dto.Photographer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoriesReqDto {
    private long categoryId;
    private int price;
    private String description;
}
