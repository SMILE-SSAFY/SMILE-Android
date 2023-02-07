package com.ssafy.core.repository.reservation;

import com.ssafy.TestConfig;
import com.ssafy.core.entity.Reservation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("real")
@Import(TestConfig.class)
class ReservationRepositoryTest {

    @Autowired
    ReservationRepository reservationRepository;

    @Test
    void test() throws ParseException {
        LocalDate date = LocalDate.of(2023, 2, 21);

        List<Reservation> findReservation = reservationRepository.findByReservedAt(Date.valueOf(date));
        System.out.println(findReservation);

    }

}