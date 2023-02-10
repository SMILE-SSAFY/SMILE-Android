package com.ssafy.batch.job;

import com.ssafy.batch.service.NotificationService;
import com.ssafy.core.code.ReservationStatus;
import com.ssafy.core.entity.Reservation;
import com.ssafy.core.repository.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChangeReservingJobConfig {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;
    public final ReservationRepository reservationRepository;
    public final NotificationService notificationService;

    @Bean
    public Job changeReservingJob(Step changeReservingStep) {

        return jobBuilderFactory.get("changeReservingJob")
                .incrementer(new RunIdIncrementer())
                .start(changeReservingStep)
                .build();
    }

    @Bean
    @JobScope
    public Step changeReservingStep(ItemReader reservationNowReader,
                                    ItemWriter reservationNowWriter) {
        return stepBuilderFactory.get("changeReservingStep")
                .<Reservation, Reservation>chunk(10)
                .reader(reservationNowReader)
                .writer(reservationNowWriter)
                .build();
    }

    /**
     * db 조회하는 Reader
     *
     * @return
     */
    @Bean
    @StepScope
    public RepositoryItemReader<Reservation> reservationNowReader() {
        return new RepositoryItemReaderBuilder<Reservation>()
                .name("reservationNowReader")
                .repository(reservationRepository)
                .methodName("findByReservedAtAndStatusNot")
                .pageSize(10)
                .arguments(Date.valueOf(LocalDate.now()), ReservationStatus.예약취소)
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<Reservation> reservationNowWriter() {
        return new ItemWriter<Reservation>() {
            @Override
            public void write(List<? extends Reservation> reservationList) throws Exception {
                log.info("changeReservingjob-------------");
                for (Reservation reservation : reservationList) {
                    if (reservation.getStatus().equals(ReservationStatus.예약확정전)) {
                        notificationService.cancelReservation(reservation);
                    } else {
                        reservation.updateStatus(ReservationStatus.예약진행중);
                        reservationRepository.save(reservation);
                    }
                }
            }
        };
    }
}
