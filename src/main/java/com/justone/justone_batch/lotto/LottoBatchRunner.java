package com.justone.justone_batch.lotto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LottoBatchRunner {
    private final LottoBatchService lottoBatchService;
    public void run() {
        lottoBatchService.fetchLatestDraw();
    }
}
