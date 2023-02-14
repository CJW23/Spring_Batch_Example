package com.cjw.springbatch.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JobTestRunner implements ApplicationRunner {
    private final JobLauncher jobLauncher;
    private final Job job;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        jobLauncher.run(job, this.jobExecutionFailedTest());
    }

    private JobParameters jobExecutionCompletedTest() {
        return new JobParametersBuilder()
                .addString("jobExecutionCompletedTest", "test")
                .toJobParameters();
    }

    private JobParameters jobExecutionFailedTest() {
        return new JobParametersBuilder()
                .addString("jobExecutionFailedTest2", "test2")
                .toJobParameters();
    }

    private JobParameters jobParametersTest() {
        return new JobParametersBuilder()
                .addString("string", "string param")
                .addLong("long", 1234L)
                .addDate("date", new Date())
                .addDouble("double", 1.2)
                .toJobParameters();
    }
}
