package com.ssafy.api.dto.Photographer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사진작가 좋아요가 되어있는지 확인하는 DTO
 *
 * @author 신민철
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotographerHeartDto {
    private Long photographerId;
    private Boolean isHeart;
}
