package com.ssafy.core.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 예약 상태 확인 ENUM
 *
 * @author 김정은
 */
@Getter
@RequiredArgsConstructor
public enum ReservationStatus {
    예약확정전,
    예약확정,
    예약진행중,
    완료,
    예약취소

}
