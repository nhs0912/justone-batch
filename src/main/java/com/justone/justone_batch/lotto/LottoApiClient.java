package com.justone.justone_batch.lotto;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class LottoApiClient {
	private final RestClient restClient;
	private final URI baseUri;

	public LottoApiClient(
		RestClient.Builder restClientBuilder,
		@Value("${lotto.api.base-url:https://www.dhlottery.co.kr/common.do}") String baseUrl
	) {
		this.restClient = restClientBuilder.build();
		this.baseUri = URI.create(baseUrl);
	}

	public LottoApiResponse fetchLatest(int drawNo) {
		URI uri = UriComponentsBuilder.fromUri(baseUri)
			.queryParam("method", "getLottoNumber")
			.queryParam("drwNo", drawNo)
			.build()
			.toUri();

		return restClient.get()
			.uri(uri)
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.body(LottoApiResponse.class);
	}
}
