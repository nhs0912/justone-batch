package com.justone.justone_batch.config;

import com.justone.justone_batch.lotto.service.LottoBatchService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class LottoBatchJobConfig {


    @Bean
    public Job lottoJob(JobRepository jobRepository, Step lottoStep) {
        return new JobBuilder("lottoJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
                .start(lottoStep)
                .build();
    }

    @Bean
    public Step lottoStep(
            JobRepository jobRepository, PlatformTransactionManager transactionManager, Tasklet lottoTasklet
    ) {
        return new StepBuilder("lottoStep", jobRepository)
                .tasklet(lottoTasklet, transactionManager).build();
    }

    @Bean
    @StepScope
    public Tasklet lottoTasklet(@Value("#{jobParameters['lotteryNo']}") final String lotteryNo, final LottoBatchService lottoBatchService) {
        return ((contribution, chunkCountext) -> {
            lottoBatchService.fetchDraw(lotteryNo);
            return RepeatStatus.FINISHED;
        });
    }

}
