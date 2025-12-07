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
public class ByeWorldJobBatchConfig {
    @Bean
    public Job byeWorldJob(
            JobRepository jobRepository,
            Step byeWorld1Step,
            Step byeWorld2Step
    ) {
        return new JobBuilder("byeWorldJob", jobRepository)
                .start(byeWorld1Step)
                .next(byeWorld2Step)
                .build();
    }

    @Bean
    public Step byeWorld1Step(JobRepository jobRepository) {
        return new StepBuilder("byeWorld1Step", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("byeWorld1Step : first bye");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step byeWorld2Step(JobRepository jobRepository) {
        return new StepBuilder("byeWorld2Step", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("byeWorld2Step : second bye");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
