package com.ssafy.api.dto.Photographer;

import com.ssafy.core.entity.Categories;
import com.ssafy.core.entity.Photographer;
import com.ssafy.core.entity.Places;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 사진작가 DTO
 *
 * author @김정은
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotographerReqDto {
    private Long photographerId;
    private String profileImg;
    private String introduction;
    private String account;
    private List<Places> places;
    private List<Categories> categories;
    private boolean isDeleted;
}
