package com.ssafy.api.dto.Photographer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotographerUpdateReqDto {
    private Long photographerId;
    private String profileImg;
    private String introduction;
    private String account;
    private List<PlacesUpdateReqDto> places;
    private List<CategoriesUpdateReqDto> categories;
    private boolean isDeleted;
}
