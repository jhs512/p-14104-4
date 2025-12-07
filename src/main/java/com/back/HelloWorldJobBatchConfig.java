package com.back;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldJobBatchConfig {
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
