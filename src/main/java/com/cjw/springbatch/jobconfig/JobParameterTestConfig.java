package com.cjw.springbatch.jobconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class JobParameterTestConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /*@Bean
    public Job jobParameterJob() {
        return jobBuilderFactory.get("jobParameter")
                .start(jobParametersTestStep())
                .build();
    }*/

    /**
     * JobParameters Test
     */
    @Bean
    public Step jobParametersTestStep() {
        return stepBuilderFactory.get("jobParametersTest")
                //JobParameters Test
                //JobParameters 가져오는 방법
                .tasklet((contribution, chunkContext) -> {
                    //방법1
                    JobParameters jobParameters = contribution.getStepExecution().getJobParameters();
                    String string = jobParameters.getString("string");
                    Date date = jobParameters.getDate("date");
                    Long aLong = jobParameters.getLong("long");
                    Double aDouble = jobParameters.getDouble("double");

                    //방법2
                    Map<String, Object> jobParameters1 = chunkContext.getStepContext().getJobParameters();

                    return RepeatStatus.FINISHED;
                }).build();
    }
}
