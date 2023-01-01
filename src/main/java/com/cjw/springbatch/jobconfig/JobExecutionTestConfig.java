package com.cjw.springbatch.jobconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JobExecutionTestConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /*@Bean
    public Job jobExecutionJob() {
        return jobBuilderFactory.get("jobExecution")
                .start(jobExecutionFailedTestStep())
                .build();
    }*/

    /**
     * JobExecutionTest Completed
     */
    @Bean
    public Step jobExecutionCompletedTestStep() {
        return stepBuilderFactory.get("jobExecutionTestCompleted")
                //JobExecutionTest
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("=====================");
                    System.out.println(">> JobExecutionTest ");
                    System.out.println("=====================");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    /**
     * JobExecutionTest Failed
     */
    @Bean
    public Step jobExecutionFailedTestStep() {
        return stepBuilderFactory.get("jobExecutionTestFailed")
                //JobExecutionTest
                .tasklet((contribution, chunkContext) -> {
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
