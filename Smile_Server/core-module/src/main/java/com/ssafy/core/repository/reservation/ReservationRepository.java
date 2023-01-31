package com.ssafy.core.repository.reservation;

import com.ssafy.core.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 예약 관련 레포지토리
 *
 * @author 김정은
 * @author 서재건
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
    List<Reservation> findReservationsByPhotographerId(Long photographerId);

    List<Reservation> findReservationsByUserId(Long userId);
}
