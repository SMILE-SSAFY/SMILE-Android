package com.ssafy.api.dto.Photographer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 사진작가 요청 DTO
 *
 * @author 김정은
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotographerReqDto {
    private Long photographerId;
    private String profileImg;
    private String introduction;
    private String bank;
    private String account;
    private List<PlacesReqDto> places;
    private List<CategoriesReqDto> categories;
}
