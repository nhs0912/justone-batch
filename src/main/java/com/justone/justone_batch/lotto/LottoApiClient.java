package com.justone.justone_batch.lotto;


import com.justone.justone_batch.lotto.dto.LottoApiWrapperResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;

@Slf4j
@Component
public class LottoApiClient {
    private final RestClient restClient;
    private final URI baseUri;

    public LottoApiClient(
            RestClient.Builder restClientBuilder,
            ObjectMapper objectMapper,
            @Value("${lotto.api.base-url:https://www.dhlottery.co.kr/lt645/selectPstLt645Info.do}") String baseUrl
    ) {
        this.restClient = restClientBuilder.build();
        this.baseUri = URI.create(baseUrl);
    }

    public LottoApiWrapperResponse fetchLatest(String lotteryNo) {
        URI uri = UriComponentsBuilder.fromUri(baseUri)
                .queryParam("srchLtEpsd", lotteryNo)
                .queryParam("_", "1767334036641")
                .build()
                .toUri();

        LottoApiWrapperResponse responseBody = restClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(LottoApiWrapperResponse.class); // ✅ RestClient 방식


        try {
            if (responseBody == null) {
                throw new IllegalStateException("Received empty body from Lotto API for ltEpsd " + lotteryNo);
            }
            log.info("로또 전체 회차 = {}", responseBody.data().list().size());
            return responseBody;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON response from Lotto API. Body: " + responseBody, e);
        }
    }
}
