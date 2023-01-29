package com.ssafy.core.repository;


import java.sql.Date;
import java.util.List;

public interface ReservationRepositoryCustom {

    List<Date> findReservedAtByPhotographerIdAndReservedAt(Long photographerId, Date now);
}
