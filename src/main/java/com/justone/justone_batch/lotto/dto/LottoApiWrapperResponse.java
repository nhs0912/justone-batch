package com.justone.justone_batch.lotto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record LottoApiWrapperResponse(
        @JsonProperty("resultCode") String resultCode,
        @JsonProperty("resultMessage") String resultMessage,
        @JsonProperty("data") LottoApiData data
) {
    public record LottoApiData(
            @JsonProperty("list") List<LottoApiResponse> list
    ) {}
}
