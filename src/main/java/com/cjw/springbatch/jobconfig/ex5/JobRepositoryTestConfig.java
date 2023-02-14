package com.cjw.springbatch.jobconfig.ex5;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
//@Configuration
public class JobRepositoryTestConfig {
    private final StepBuilderFactory stepBuilderFactory;
    private final JobExecutionListener jobExecutionListener;    //job 시작 전, 후에 실행할 코드 정의?
    private final JobBuilderFactory jobBuilderFactory;

    /*@Bean
    public Job batchJob() {
        return this.jobBuilderFactory.get("jobRepositoryTest")
                .start(step1())
                .listener(jobExecutionListener)
                .build();
    }*/

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step12")
                .tasklet((contribution, chunkContext) -> {
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
