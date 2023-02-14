package com.ssafy;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableBatchProcessing
@ComponentScan({"com.ssafy.core"})
@ComponentScan({"com.ssafy.batch"})
@EntityScan("com.ssafy.core")
@EnableJpaRepositories("com.ssafy.core")
@SpringBootApplication
public class SpringBatchApplication {

    public static void main(String[] args) {
        System.setProperty("spring.config.name",
                "application,application-real,application-aws,application-coolsms,application-login,application-pay");
        SpringApplication.run(SpringBatchApplication.class, args);
    }

}