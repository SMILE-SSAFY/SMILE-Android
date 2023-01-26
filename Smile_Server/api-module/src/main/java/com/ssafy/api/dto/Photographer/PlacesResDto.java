package com.ssafy.api.dto.Photographer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlacesResDto {
    private long placeId;
    private String first;
    private String second;
}
