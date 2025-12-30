package com.justone.justone_batch.lotto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record LottoApiResponse(
	@JsonProperty("returnValue") String returnValue,
	@JsonProperty("drwNo") int drwNo,
	@JsonProperty("drwNoDate") String drwDate,
	@JsonProperty("drwtNo1") int number1,
	@JsonProperty("drwtNo2") int number2,
	@JsonProperty("drwtNo3") int number3,
	@JsonProperty("drwtNo4") int number4,
	@JsonProperty("drwtNo5") int number5,
	@JsonProperty("drwtNo6") int number6,
	@JsonProperty("bnusNo") int bonusNumber,
	@JsonProperty("firstPrzwnerCo") Integer firstPrzwnerCo,
	@JsonProperty("firstWinamnt") BigDecimal firstWinAmnt,
	@JsonProperty("firstAccumamnt") BigDecimal firstAccumAmnt,
	@JsonProperty("totSellamnt") BigDecimal totSellAmnt
) {}
