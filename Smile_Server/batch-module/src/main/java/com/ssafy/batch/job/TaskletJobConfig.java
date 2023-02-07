package com.ssafy.batch.job;

import com.ssafy.core.entity.Reservation;
import com.ssafy.core.repository.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * --spring.batch.job.names=taskletJob
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class TaskletJobConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public ReservationRepository reservationRepository;

    @Bean
    public Job TaskletJob() {

        return jobBuilderFactory.get("taskletJob")
                .start(TaskStep())
                .build();
    }

    @Bean
    @JobScope
    public Step TaskStep() {
        return stepBuilderFactory.get("taskletStep")
                .tasklet(myTasklet())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet myTasklet() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                log.info("스프링 배치 실행");
//                LocalDate date = LocalDate.now().plusDays(1);
                LocalDate date = LocalDate.of(2023, 2, 21);
                List<Reservation> reservationList = reservationRepository.findByReservedAt(Date.valueOf(date));
                log.info("--------------" + reservationList.toString());


                return RepeatStatus.FINISHED;
            }
        };
    }

}

