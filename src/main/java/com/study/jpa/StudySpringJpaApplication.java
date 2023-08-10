package com.study.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class StudySpringJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudySpringJpaApplication.class, args);
    }

}
