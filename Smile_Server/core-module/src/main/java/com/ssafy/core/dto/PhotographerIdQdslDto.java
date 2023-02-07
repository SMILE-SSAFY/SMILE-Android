package com.ssafy.core.dto;

import com.ssafy.core.entity.Photographer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * querydsl 매핑 dto
 *
 * @author 이지윤
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotographerIdQdslDto {
    private Long photographerId;
}
