package com.cjw.springbatch.jobconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class ExecutionContextTestConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public Job jobExecutionJob() {
        return jobBuilderFactory.get("jobExecution")
                .start(executionContextTestStep1())
                .next(executionContextTestStep2())
                .next(executionContextTestStep3())
                .build();
    }

    @Bean
    public Step executionContextTestStep1() {
        return stepBuilderFactory.get("executionContextTestStep1")
                .tasklet((contribution, chunkContext) -> {
                    //Job Execution Context : JobExecution 상태를 저장하는 공유 객체
                    ExecutionContext jobExecutionContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
                    //Step Execution Contextx : StepExecution 상태를 저장하는 공유 객체
                    ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();

                    //JobName contributioin, chunkContext 두가지 경우
                    //String jobName = contribution.getStepExecution().getJobExecution().getJobInstance().getJobName();
                    String jobName = chunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName();
                    //StepName
                    String stepName = chunkContext.getStepContext().getStepExecution().getStepName();

                    jobExecutionContext.put("jobName", jobName);
                    stepExecutionContext.put("stepName", stepName);

                    System.out.println("=====================step1==================");
                    System.out.println("jobName: " + jobName + " stepName: " + stepName);
                    System.out.println("=======================================");

                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step executionContextTestStep2() {
        return stepBuilderFactory.get("executionContextTestStep2")
                .tasklet((contribution, chunkContext) -> {
                    //Job Execution Context : JobExecution 상태를 저장하는 공유 객체
                    ExecutionContext jobExecutionContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
                    //Step Execution Contextx : StepExecution 상태를 저장하는 공유 객체
                    ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();

                    //JobName contributioin, chunkContext 두가지 경우
                    //String jobName = contribution.getStepExecution().getJobExecution().getJobInstance().getJobName();
                    String jobName = chunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName();
                    //StepName
                    String stepName = chunkContext.getStepContext().getStepExecution().getStepName();

                    System.out.println("=====================step2==================");
                    System.out.println(jobExecutionContext.get("jobName"));
                    System.out.println("jobName: " + jobName + " stepName: " + stepName);
                    System.out.println("=======================================");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step executionContextTestStep3() {
        return stepBuilderFactory.get("executionContextTestStep3")
                .tasklet((contribution, chunkContext) -> {
                    //Job Execution Context : JobExecution 상태를 저장하는 공유 객체
                    ExecutionContext jobExecutionContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
                    //Step Execution Contextx : StepExecution 상태를 저장하는 공유 객체
                    ExecutionContext stepExecutionContext = contribution.getStepExecution().getExecutionContext();

                    //JobName contributioin, chunkContext 두가지 경우
                    //String jobName = contribution.getStepExecution().getJobExecution().getJobInstance().getJobName();
                    String jobName = chunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName();
                    //StepName
                    String stepName = chunkContext.getStepContext().getStepExecution().getStepName();
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
