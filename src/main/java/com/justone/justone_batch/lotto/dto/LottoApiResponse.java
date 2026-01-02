package com.justone.justone_batch.lotto.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.justone.justone_batch.lotto.entity.LottoResult;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Builder
public record LottoApiResponse(

        // 기본 정보
        @JsonProperty("ltEpsd")
        Integer lotteryNo,                // 회차

        @JsonProperty("ltRflYmd")
        String lotteryDate,               // 추첨일 (yyyyMMdd)

        // 당첨 번호
        @JsonProperty("tm1WnNo")
        Integer number1,

        @JsonProperty("tm2WnNo")
        Integer number2,

        @JsonProperty("tm3WnNo")
        Integer number3,

        @JsonProperty("tm4WnNo")
        Integer number4,

        @JsonProperty("tm5WnNo")
        Integer number5,

        @JsonProperty("tm6WnNo")
        Integer number6,

        @JsonProperty("bnsWnNo")
        Integer bonusNumber,

        // 1등
        @JsonProperty("rnk1WnNope")
        Integer rank1WinnerCount,

        @JsonProperty("rnk1WnAmt")
        BigDecimal rank1WinnerAmount,

        @JsonProperty("rnk1SumWnAmt")
        BigDecimal rank1SumWinnerAmount,

        // 2등
        @JsonProperty("rnk2WnNope")
        Integer rank2WinnerCount,

        @JsonProperty("rnk2WnAmt")
        BigDecimal rank2WinnerAmount,

        @JsonProperty("rnk2SumWnAmt")
        BigDecimal rank2SumWinnerAmount,

        // 3등
        @JsonProperty("rnk3WnNope")
        Integer rank3WinnerCount,

        @JsonProperty("rnk3WnAmt")
        BigDecimal rank3WinnerAmount,

        @JsonProperty("rnk3SumWnAmt")
        BigDecimal rank3SumWinnerAmount,
        // 4등
        @JsonProperty("rnk4WnNope")
        Integer rank4WinnerCount,

        @JsonProperty("rnk4WnAmt")
        BigDecimal rank4WinnerAmount,

        @JsonProperty("rnk4SumWnAmt")
        BigDecimal rank4SumWinnerAmount,

        // 5등
        @JsonProperty("rnk5WnNope")
        Integer rank5WinnerCount,

        @JsonProperty("rnk5WnAmt")
        BigDecimal rank5WinnerAmount,

        @JsonProperty("rnk5SumWnAmt")
        BigDecimal rank5SumWinnerAmount,

        // 판매 금액
        @JsonProperty("rlvtEpsdSumNtslAmt")
        BigDecimal totalSalesAmount,

        // 기타
        @JsonProperty("sumWnNope")
        Integer sumWinnerCount,

        @JsonProperty("excelRnk")
        String excelRank

) {
    private static final DateTimeFormatter BASIC_YYYYMMDD = DateTimeFormatter.BASIC_ISO_DATE;

    public LottoResult toEntity() {
        return LottoResult.builder()
                .lotteryNo(lotteryNo)
                .lotteryDate(parseLotteryDate(lotteryDate))
                .number1(number1)
                .number2(number2)
                .number3(number3)
                .number4(number4)
                .number5(number5)
                .number6(number6)
                .bonusNumber(bonusNumber)
                .rank1WinnerCount(nullToZero(rank1WinnerCount))
                .rank1WinnerAmount(nullToZero(rank1WinnerAmount))
                .rank1SumWinnerAmount(nullToZero(rank1SumWinnerAmount))
                .rank2WinnerCount(nullToZero(rank2WinnerCount))
                .rank2WinnerAmount(nullToZero(rank2WinnerAmount))
                .rank2SumWinnerAmount(nullToZero(rank2SumWinnerAmount))
                .rank3WinnerCount(nullToZero(rank3WinnerCount))
                .rank3WinnerAmount(nullToZero(rank3WinnerAmount))
                .rank3SumWinnerAmount(nullToZero(rank3SumWinnerAmount))
                .rank4WinnerCount(nullToZero(rank4WinnerCount))
                .rank4WinnerAmount(nullToZero(rank4WinnerAmount))
                .rank4SumWinnerAmount(nullToZero(rank4SumWinnerAmount))
                .rank5WinnerCount(nullToZero(rank5WinnerCount))
                .rank5WinnerAmount(nullToZero(rank5WinnerAmount))
                .rank5SumWinnerAmount(nullToZero(rank5SumWinnerAmount))
                .sumWinnerCount(nullToZero(sumWinnerCount))
                .totalSalesAmount(nullToZero(totalSalesAmount))
                .excelRank(excelRank)
                // 엔티티에 gmSqNo가 있으면 여기에 세팅 (DTO에 추가하면 됨)
                .build();
    }

    private static LocalDate parseLotteryDate(String yyyymmdd) {
        if (yyyymmdd == null || yyyymmdd.isBlank()) {
            throw new IllegalArgumentException("lotteryDate(ltRflYmd) is null/blank");
        }
        return LocalDate.parse(yyyymmdd, BASIC_YYYYMMDD);
    }

    private static Integer nullToZero(Integer v) {
        return v == null ? 0 : v;
    }

    private static BigDecimal nullToZero(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }
}

