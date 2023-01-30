package com.ssafy.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriesQdslDto {
    private Long id;
    private String name;
    private int price;
    private String description;
}
