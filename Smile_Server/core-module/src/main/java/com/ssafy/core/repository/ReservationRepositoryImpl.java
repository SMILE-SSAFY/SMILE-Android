package com.ssafy.core.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.core.code.ReservationStatus;
import com.ssafy.core.entity.QReservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.sql.Date;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Date> findReservedAtByPhotographerIdAndReservedAt(Long photographerId, Date now){
        QReservation reservation = QReservation.reservation;

        return jpaQueryFactory
                .select(reservation.reservedAt)
                .from(reservation)
                .where(reservation.photographer.id.eq(photographerId),
                        reservation.reservedAt.goe(now),
                        reservation.status.ne(ReservationStatus.예약취소))
                .fetch();
    }
}
