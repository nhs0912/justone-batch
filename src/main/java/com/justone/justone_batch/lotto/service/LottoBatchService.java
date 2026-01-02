package com.justone.justone_batch.lotto.service;

import com.justone.justone_batch.lotto.LottoApiClient;
import com.justone.justone_batch.lotto.entity.LottoResult;
import com.justone.justone_batch.lotto.repository.LottoDrawResultRepository;
import com.justone.justone_batch.lotto.dto.LottoApiResponse;
import com.justone.justone_batch.lotto.dto.LottoApiWrapperResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

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
    public void fetchDraw(String lotteryNo) {

        log.info("lotteryNo ==={}", lotteryNo);
        if(!StringUtils.hasText(lotteryNo)){
            lotteryNo = "all";
        }
        LottoApiWrapperResponse wrapper = lottoApiClient.fetchLatest(lotteryNo);

        var list = Optional.ofNullable(wrapper)
                .map(LottoApiWrapperResponse::data)
                .map(LottoApiWrapperResponse.LottoApiData::list)
                .orElseThrow(() -> new IllegalStateException("No lotto data for lotteryNo "));
        if (list.isEmpty()) {
            log.info("Empty list. lotteryNo={}", lotteryNo);
            return;
        }

        var entities = list.stream().map(LottoApiResponse::toEntity).toList();
        var ids = entities.stream().map(LottoResult::getLotteryNo).toList();

        var existingIds = lottoDrawResultRepository.findAllById(ids).stream()
                .map(LottoResult::getLotteryNo)
                .collect(java.util.stream.Collectors.toSet());
        var toSave = entities.stream()
                .filter(e -> !existingIds.contains(e.getLotteryNo()))
                .toList();

        lottoDrawResultRepository.saveAll(toSave);
        log.info("Inserted {} new rows (out of {})", toSave.size(), entities.size());
    }
}
