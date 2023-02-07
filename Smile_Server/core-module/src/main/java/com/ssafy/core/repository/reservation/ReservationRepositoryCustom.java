package com.ssafy.core.repository.reservation;


import java.sql.Date;
import java.util.List;

/**
 * 추천 관련 레포지토리
 *
 * @author 이지윤
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
