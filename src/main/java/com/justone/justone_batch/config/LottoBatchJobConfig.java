package com.justone.justone_batch.config;

import com.justone.justone_batch.lotto.LottoBatchService;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class LottoBatchJobConfig {

    // ================== lottoJob (최신 회차) 관련 설정 ==================

    @Bean
    public Job lottoJob(JobRepository jobRepository, @Qualifier("lottoStep") Step lottoStep) {
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

    // ================== lottoSelectJob (범위 지정) 관련 설정 ==================

    @Bean
    public Job lottoSelectJob(JobRepository jobRepository, @Qualifier("lottoSelectStep") Step lottoSelectStep) {
        return new JobBuilder("lottoSelectJob", jobRepository)
                //.incrementer(new RunIdIncrementer())
                .start(lottoSelectStep)
                .build();
    }

    /**
     * StepScope 문제를 해결하기 위해 Step과 Tasklet을 분리합니다.
     * 이 Step은 일반 빈으로, 프록시 역할을 하는 Tasklet을 주입받습니다.
     */
    @Bean
    public Step lottoSelectStep(JobRepository jobRepository,
                                PlatformTransactionManager transactionManager,
                                @Qualifier("lottoSelectTasklet") Tasklet lottoSelectTasklet) {
        return new StepBuilder("lottoSelectStep", jobRepository)
                .tasklet(lottoSelectTasklet, transactionManager) // 주입받은 Tasklet 사용
                .build();
    }

    /**
     * 실제 로직을 수행하는 Tasklet입니다.
     * 여기에 @StepScope를 적용하여 Job 파라미터를 안전하게 주입받습니다.
     */
    @Bean
    @StepScope
    public Tasklet lottoSelectTasklet(LottoBatchService lottoBatchService,
                                      @Value("#{jobParameters['start']}") Integer start,
                                      @Value("#{jobParameters['end']}") Integer end) {
        return (contribution, chunkContext) -> {
            lottoBatchService.fetchAllDraw(start, end);
            return RepeatStatus.FINISHED;
        };
    }
}
