package com.back;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;

@Configuration
public class HelloWorldJobConfig {
    private final HelloWorldJobConfig self;
    private final JobOperator jobOperator;

    public HelloWorldJobConfig(
            @Lazy HelloWorldJobConfig self,
            JobOperator jobOperator
    ) {
        this.self = self;
        this.jobOperator = jobOperator;
    }

    @Bean
    public ApplicationRunner runHelloWorldJobApplicationRunner(Job helloWorldJob) {
        return args -> {
            System.out.println("job : " + helloWorldJob);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("runTime", LocalDateTime.now().toString())
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution execution = jobOperator.start(helloWorldJob, jobParameters);
        };
    }

    @Bean
    public Job helloWorldJob(
            JobRepository jobRepository,
            Step helloWorld1Step,
            Step helloWorld2Step
    ) {
        return new JobBuilder("helloWorldJob", jobRepository)
                .start(helloWorld1Step)
                .next(helloWorld2Step)
                .build();
    }

    @Bean
    public Step helloWorld1Step(JobRepository jobRepository) {
        return new StepBuilder("helloWorld1Step", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("helloWorld1Step : first hello");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step helloWorld2Step(JobRepository jobRepository) {
        return new StepBuilder("helloWorld2Step", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("helloWorld2Step : second hello");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
