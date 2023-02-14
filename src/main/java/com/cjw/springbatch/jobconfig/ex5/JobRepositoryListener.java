package com.cjw.springbatch.jobconfig.ex5;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobRepositoryListener implements JobExecutionListener {
    private final JobRepository jobRepository;

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    /**
     * Job 수행후 코드 실행
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        //내가 필요한 데이터의 requset(날짜 등)를 얻어서 마지막 jobExecution을 가져옴
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("jobExecutionFailedTest", "test").toJobParameters();
        JobExecution lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParameters);
        if(lastJobExecution != null) {
            for(StepExecution execution : lastJobExecution.getStepExecutions()) {
                System.out.println("existStatus: " + execution.getExitStatus());
                System.out.println("status: " + execution.getStatus());
                System.out.println("stepName: " + execution.getStepName());
            }
        }
    }
}
