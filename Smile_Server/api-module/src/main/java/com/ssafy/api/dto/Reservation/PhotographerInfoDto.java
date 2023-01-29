package com.ssafy.api.dto.Reservation;

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
    private Long photographerId;
    private List<Date> days;
    private List<CategoriesInfoResDto> categories;
}
