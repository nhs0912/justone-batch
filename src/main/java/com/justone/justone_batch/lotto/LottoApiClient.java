package com.justone.justone_batch.lotto;


import java.net.URI;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class LottoApiClient {
	private final RestClient restClient;
	private final URI baseUri;
	private final ObjectMapper objectMapper;

	public LottoApiClient(
		RestClient.Builder restClientBuilder,
		ObjectMapper objectMapper,
		@Value("${lotto.api.base-url:https://www.dhlottery.co.kr/common.do}") String baseUrl
	) {
		this.restClient = restClientBuilder.build();
		this.objectMapper = objectMapper;
		this.baseUri = URI.create(baseUrl);
	}

	public LottoApiResponse fetchLatest(int drwNo) {
		URI uri = UriComponentsBuilder.fromUri(baseUri)
			.queryParam("method", "getLottoNumber")
			.queryParam("drwNo", drwNo)
			.build()
			.toUri();

		String responseBody = restClient.get()
			.uri(uri)
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.body(String.class);
		
		try {
			if (responseBody == null) {
				throw new IllegalStateException("Received empty body from Lotto API for drawNo " + drwNo);
			}
			log.info("responseBody = {}", responseBody);
			return objectMapper.readValue(responseBody, LottoApiResponse.class);
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse JSON response from Lotto API. Body: " + responseBody, e);
		}
	}
}
