package com.ssafy.api.dto.Reservation;

import com.ssafy.api.dto.Photographer.PlacesForListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

/**
 * 사진작가 정보 조회 DTO
 *
 * @author 김정은
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotographerInfoDto {
    private List<Date> days;
    private List<CategoriesInfoResDto> categories;
    private List<PlacesForListDto> places;
}
