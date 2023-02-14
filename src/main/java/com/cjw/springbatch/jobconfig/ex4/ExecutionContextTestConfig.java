package com.cjw.springbatch.jobconfig.ex4;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * 첫 시도
 * Step1: 각 jobExecutionContext, stepExecutionContext에 jobName, stepName 저장 후 출력
 * Step2: Step1에서 저장한 JobExecutionContext의 jobName 출력, JobExecutionContext에 name:user1 저장 후 에러 발생
 *
 * 두번째 시도(실패 지점부터 다시 시작 하므로 Step2부터 시작)
 * Step3: Step2에서 저장한 JobExecutionContext의 name값 출력
 */
@RequiredArgsConstructor
//@Configuration
public class ExecutionContextTestConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /*@Bean
    public Job jobExecutionJob() {
        return jobBuilderFactory.get("jobExecution")
                .start(executionContextTestStep1())
                .next(executionContextTestStep2())
                .next(executionContextTestStep3())
                .build();
    }*/

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

                    //저장
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

                    //JobName contributioin, chunkContext 두가지 경우
                    //String jobName = contribution.getStepExecution().getJobExecution().getJobInstance().getJobName();
                    String jobName = chunkContext.getStepContext().getStepExecution().getJobExecution().getJobInstance().getJobName();
                    //StepName
                    String stepName = chunkContext.getStepContext().getStepExecution().getStepName();

                    System.out.println("=====================step2==================");
                    //Step1에서 저장한 jobName이 출력될 것임
                    System.out.println(jobExecutionContext.get("jobName"));
                    System.out.println("jobName: " + jobName + " stepName: " + stepName);
                    System.out.println("=======================================");

                    Object name = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("name");
                    //Job 첫 시도는 에러 나게끔 유발
                    if(isEmpty(name)) {
                        //저장되고 에러가 발생해도 다음 시도때 저장되어 있을 것인가
                        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("name", "user1");
                        throw new RuntimeException("에러");
                    }

                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step executionContextTestStep3() {
        return stepBuilderFactory.get("executionContextTestStep3")
                .tasklet((contribution, chunkContext) -> {
                    //Step3에서 저장한 name이 출력되는지
                    Object name = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("name");
                    System.out.println("=====================step3==================");
                    System.out.println("name : " + name);
                    System.out.println("=======================================");
                    return RepeatStatus.FINISHED;
                }).build();
    }
}
