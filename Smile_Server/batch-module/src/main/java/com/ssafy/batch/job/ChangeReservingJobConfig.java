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

/**
 * 당일 예약 상태 변경 JobConfig
 *
 * @author 서재건
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChangeReservingJobConfig {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;
    public final ReservationRepository reservationRepository;
    public final NotificationService notificationService;

    /**
     * 예약 상태 변경 Job
     *
     * @param changeReservingStep
     * @return
     */
    @Bean
    public Job changeReservingJob(Step changeReservingStep) {

        return jobBuilderFactory.get("changeReservingJob")
                .incrementer(new RunIdIncrementer())
                .start(changeReservingStep)
                .build();
    }

    /**
     * 예약 상태 변경 Step
     *
     * @param reservationNowReader
     * @param reservationNowWriter
     * @return
     */
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

    /**
     * 확정 전이면 예약 취소
     * 확정이면 예약 진행중
     *
     * @return
     */
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
