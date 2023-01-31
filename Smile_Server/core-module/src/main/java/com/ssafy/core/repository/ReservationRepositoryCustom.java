package com.ssafy.core.repository;


import java.sql.Date;
import java.util.List;

/**
 * 예약 관련 커스텀 레포지토리
 *
 * @author 김정은
 */
public interface ReservationRepositoryCustom {

    /**
     * photographerId와 예약일자로 사진작가별 예약된 날짜 조회
     *
     * @param photographerId
     * @param now
     * @return List<Date>
     */
    List<Date> findReservedAtByPhotographerIdAndReservedAt(Long photographerId, Date now);
}
