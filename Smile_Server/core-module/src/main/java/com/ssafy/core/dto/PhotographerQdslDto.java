package com.ssafy.core.dto;

import com.ssafy.core.entity.Photographer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotographerQdslDto {
    private Photographer photographer;
    private Long heart;
    private boolean hasHeart;
}
