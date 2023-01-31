package com.ssafy.core.repository;

import com.ssafy.core.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 예약 관련 레포지토리
 *
 * @author 김정은
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
}
