package com.ssafy.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collections;

/**
 * Job을 특정 주기로 실행시키는 Scheduler 클래스
 * @author 서재건
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class JobScheduler {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry);
        return postProcessor;
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void notificationRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime", new JobParameter(System.currentTimeMillis()))
        );

        try {
            jobLauncher.run(jobRegistry.getJob("notificationJob"), jobParameters);
        } catch (NoSuchJobException e) {
            log.info("notificationJob 을 찾을 수 없습니다.");
            throw new RuntimeException(e);
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void statusRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime", new JobParameter(System.currentTimeMillis()))
        );

        try {
            jobLauncher.run(jobRegistry.getJob("changeReservingJob"), jobParameters);
        } catch (NoSuchJobException e) {
            log.info("changeReservingJob 를 찾을 수 없습니다.");
            throw new RuntimeException(e);
        }
    }

}
