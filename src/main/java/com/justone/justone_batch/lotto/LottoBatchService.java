package com.justone.justone_batch.lotto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class LottoBatchService {
    private static final Logger logger = LoggerFactory.getLogger(LottoBatchService.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final LottoApiClient lottoApiClient;
    private final LottoDrawResultRepository lottoDrawResultRepository;

    public LottoBatchService(LottoApiClient lottoApiClient, LottoDrawResultRepository lottoDrawResultRepository) {
        this.lottoApiClient = lottoApiClient;
        this.lottoDrawResultRepository = lottoDrawResultRepository;
    }

    @Transactional
    public void fetchLatestDraw() {
        int nextDrawNo = lottoDrawResultRepository.findTopByOrderByDrwNoDesc()
                .map(LottoResult::getDrwNo)
                .map(last -> last + 1)
                .orElse(1);
        log.info("nextDrawNo === {}", nextDrawNo);
        LottoApiResponse response = lottoApiClient.fetchLatest(nextDrawNo);
        if (response == null || !"success".equalsIgnoreCase(response.returnValue())) {
            logger.info("Lotto API returned no data for draw {}", nextDrawNo);
            return;
        }

        Optional<LottoResult> existing = lottoDrawResultRepository.findById(response.drwNo());
        if (existing.isPresent()) {
            logger.info("Draw {} already exists. Skipping insert.", response.drwNo());
            return;
        }

        LocalDate drawDate = LocalDate.parse(response.drwDate(), DATE_FORMATTER);
        LottoResult result = LottoResult
                .builder()
                .drwNo(response.drwNo())
                .drwDate(drawDate)
                .number1(response.number1())
                .number2(response.number2())
                .number3(response.number3())
                .number4(response.number4())
                .number5(response.number5())
                .number6(response.number6())
                .bonusNumber(response.bonusNumber())
                .firstPrzwnerCo(response.firstPrzwnerCo())
                .firstWinAmnt(response.firstWinAmnt())
                .firstAccumAmnt(response.firstAccumAmnt())
                .returnValue(response.returnValue())
                .totSellAmnt(response.totSellAmnt())
                .build();

        lottoDrawResultRepository.save(result);
        logger.info("Inserted lotto draw {}", response.drwNo());
    }

    @Transactional
    public void backfillMissingDraws(int maxAttempts) {
        for (int i = 0; i < maxAttempts; i++) {
            int nextDrawNo = lottoDrawResultRepository.findTopByOrderByDrwNoDesc()
                    .map(LottoResult::getDrwNo)
                    .map(last -> last + 1)
                    .orElse(1);

            LottoApiResponse response = lottoApiClient.fetchLatest(nextDrawNo);
            if (response == null || !"success".equalsIgnoreCase(response.returnValue())) {
                logger.info("No further draws available after {}", nextDrawNo - 1);
                return;
            }

            LocalDate drawDate = LocalDate.parse(response.drwDate(), DATE_FORMATTER);
            LottoResult result = new LottoResult(
                    response.drwNo(),
                    drawDate,
                    response.number1(),
                    response.number2(),
                    response.number3(),
                    response.number4(),
                    response.number5(),
                    response.number6(),
                    response.bonusNumber()
            );

            lottoDrawResultRepository.save(result);
        }
        logger.info("Backfill completed after {} attempts", maxAttempts);
    }

    public boolean isConfigured() {
        return Objects.nonNull(lottoApiClient);
    }
}
