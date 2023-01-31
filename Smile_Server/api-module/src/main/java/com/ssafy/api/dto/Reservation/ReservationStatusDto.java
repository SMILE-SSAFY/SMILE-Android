package com.ssafy.api.dto.Reservation;

import com.ssafy.core.code.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationStatusDto {
    private Long reservationId;
    private Long userId;
    private ReservationStatus status;
}
