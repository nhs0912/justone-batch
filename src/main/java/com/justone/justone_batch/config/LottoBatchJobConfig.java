package com.justone.justone_batch.config;

import com.justone.justone_batch.lotto.LottoBatchService;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class LottoBatchJobConfig {

    @Bean
    public Job lottoBatchJob(JobRepository jobRepository, Step lottoStep) {
        return new JobBuilder("lottoJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(lottoStep)
                .build();
    }

    @Bean
    public Step lottoStep(
            JobRepository jobRepository, PlatformTransactionManager transactionManager, LottoBatchService lottoBatchService
    ) {
        return new StepBuilder("lottoStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    lottoBatchService.fetchLatestDraw();
                    return RepeatStatus.FINISHED;
                }, transactionManager).build();

    }
}
