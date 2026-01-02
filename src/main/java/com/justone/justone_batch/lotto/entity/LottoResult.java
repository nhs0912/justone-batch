package com.justone.justone_batch.lotto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "lotto_result")
public class LottoResult {

    /**
     * 회차 (너가 말한 drwNo 역할 = ltEpsd)
     */
    @Id
    @Column(name = "lottery_no")
    private Integer lotteryNo;

    /**
     * 추첨일 (API: ltRflYmd yyyyMMdd)
     * - DB는 date 타입 권장
     */
    @Column(name = "lottery_date", nullable = false)
    private LocalDate lotteryDate;

    // 당첨번호
    @Column(name = "number1", nullable = false)
    private Integer number1;

    @Column(name = "number2", nullable = false)
    private Integer number2;

    @Column(name = "number3", nullable = false)
    private Integer number3;

    @Column(name = "number4", nullable = false)
    private Integer number4;

    @Column(name = "number5", nullable = false)
    private Integer number5;

    @Column(name = "number6", nullable = false)
    private Integer number6;

    @Column(name = "bonus_number", nullable = false)
    private Integer bonusNumber;

    // 1등
    @Column(name = "rank1_winner_count", nullable = false)
    private Integer rank1WinnerCount;

    @Column(name = "rank1_winner_amount", nullable = false, precision = 19, scale = 0)
    private BigDecimal rank1WinnerAmount;

    @Column(name = "rank1_sum_winner_amount", nullable = false, precision = 19, scale = 0)
    private BigDecimal rank1SumWinnerAmount;

    // 2등
    @Column(name = "rank2_winner_count", nullable = false)
    private Integer rank2WinnerCount;

    @Column(name = "rank2_winner_amount", nullable = false, precision = 19, scale = 0)
    private BigDecimal rank2WinnerAmount;

    @Column(name = "rank2_sum_winner_amount", nullable = false, precision = 19, scale = 0)
    private BigDecimal rank2SumWinnerAmount;

    // 3등
    @Column(name = "rank3_winner_count", nullable = false)
    private Integer rank3WinnerCount;

    @Column(name = "rank3_winner_amount", nullable = false, precision = 19, scale = 0)
    private BigDecimal rank3WinnerAmount;

    @Column(name = "rank3_sum_winner_amount", nullable = false, precision = 19, scale = 0)
    private BigDecimal rank3SumWinnerAmount;

    // 4등
    @Column(name = "rank4_winner_count", nullable = false)
    private Integer rank4WinnerCount;

    @Column(name = "rank4_winner_amount", nullable = false, precision = 19, scale = 0)
    private BigDecimal rank4WinnerAmount;

    @Column(name = "rank4_sum_winner_amount", nullable = false, precision = 19, scale = 0)
    private BigDecimal rank4SumWinnerAmount;

    // 5등
    @Column(name = "rank5_winner_count", nullable = false)
    private Integer rank5WinnerCount;

    @Column(name = "rank5_winner_amount", nullable = false, precision = 19, scale = 0)
    private BigDecimal rank5WinnerAmount;

    @Column(name = "rank5_sum_winner_amount", nullable = false, precision = 19, scale = 0)
    private BigDecimal rank5SumWinnerAmount;

    // 합계
    @Column(name = "sum_winner_count", nullable = false)
    private Integer sumWinnerCount;

    // 판매금액
    @Column(name = "total_sales_amount", nullable = false, precision = 19, scale = 0)
    private BigDecimal totalSalesAmount;

    @Column(name = "gm_sq_no", nullable = true)
    private Integer gmSqNo;

    // 엑셀 랭크
    @Column(name = "excel_rank")
    private String excelRank;


//	public LottoApiResponse toApiResponse() {
//		return LottoApiResponse.builder()
//
//				.build();
//
//	}

}
