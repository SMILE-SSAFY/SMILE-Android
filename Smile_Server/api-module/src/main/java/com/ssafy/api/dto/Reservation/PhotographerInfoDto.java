package com.ssafy.api.dto.Reservation;

import com.ssafy.api.dto.Photographer.PlacesForListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhotographerInfoDto {
    private List<Date> days;
    private List<CategoriesInfoResDto> categories;
    private List<PlacesForListDto> places;
}
