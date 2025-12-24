package com.justone.justone_batch.lotto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LottoBatchScheduler {
	private static final Logger logger = LoggerFactory.getLogger(LottoBatchScheduler.class);

	private final LottoBatchService lottoBatchService;

	public LottoBatchScheduler(LottoBatchService lottoBatchService) {
		this.lottoBatchService = lottoBatchService;
	}

	@Scheduled(cron = "${lotto.batch.cron:0 10 21 ? * SAT}", zone = "${lotto.batch.timezone:Asia/Seoul}")
	public void runWeekly() {
		logger.info("Starting weekly lotto batch");
		lottoBatchService.fetchLatestDraw();
	}
}
