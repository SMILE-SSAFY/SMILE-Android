package com.ssafy.core.repository.reservation;

import com.ssafy.TestConfig;
import com.ssafy.core.entity.Reservation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("real")
@Import(TestConfig.class)
class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;

    @Test
    void test(Pageable pageable) {
        LocalDate date = LocalDate.of(2023, 2, 21);

        Page<Reservation> findReservation = reservationRepository.findByReservedAt(Date.valueOf(date), pageable);
        System.out.println(findReservation);

    }

}