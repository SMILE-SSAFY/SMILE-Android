package com.ssafy.api.dto.Photographer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoriesResDto {
    private long categoryId;
    private String name;
    private int price;
    private String description;
}
