package com.ssafy.core.repository.reservation;

import com.ssafy.core.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * 예약 관련 레포지토리
 *
 * @author 김정은
 * @author 서재건
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
    List<Reservation> findByPhotographerIdOrderByReservedAtDescReservedTimeDesc(Long photographerId);

    List<Reservation> findByUserIdOrderByReservedAtDescReservedTimeDesc(Long userId);

    Page<Reservation> findByReservedAt(Date reservedAt, Pageable pageable);
}
