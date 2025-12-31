package com.justone.justone_batch.lotto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record LottoApiResponse(
	@JsonProperty("returnValue") String returnValue,
	@JsonProperty("drwNo") Integer drwNo,
	@JsonProperty("drwNoDate") String drwDate,
	@JsonProperty("drwtNo1") Integer number1,
	@JsonProperty("drwtNo2") Integer number2,
	@JsonProperty("drwtNo3") Integer number3,
	@JsonProperty("drwtNo4") Integer number4,
	@JsonProperty("drwtNo5") Integer number5,
	@JsonProperty("drwtNo6") Integer number6,
	@JsonProperty("bnusNo") Integer bonusNumber,
	@JsonProperty("firstPrzwnerCo") Integer firstPrzwnerCo,
	@JsonProperty("firstWinamnt") BigDecimal firstWinAmnt,
	@JsonProperty("firstAccumamnt") BigDecimal firstAccumAmnt,
	@JsonProperty("totSellamnt") BigDecimal totSellAmnt
) {}
