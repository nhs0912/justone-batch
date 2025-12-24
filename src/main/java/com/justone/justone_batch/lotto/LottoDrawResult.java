package com.justone.justone_batch.lotto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lotto_draw_result")
public class LottoDrawResult {
	@Id
	private Integer drawNo;

	@Column(nullable = false)
	private LocalDate drawDate;

	@Column(nullable = false)
	private Integer number1;

	@Column(nullable = false)
	private Integer number2;

	@Column(nullable = false)
	private Integer number3;

	@Column(nullable = false)
	private Integer number4;

	@Column(nullable = false)
	private Integer number5;

	@Column(nullable = false)
	private Integer number6;

	@Column(nullable = false)
	private Integer bonusNumber;

	protected LottoDrawResult() {
	}

	public LottoDrawResult(
		Integer drawNo,
		LocalDate drawDate,
		Integer number1,
		Integer number2,
		Integer number3,
		Integer number4,
		Integer number5,
		Integer number6,
		Integer bonusNumber
	) {
		this.drawNo = drawNo;
		this.drawDate = drawDate;
		this.number1 = number1;
		this.number2 = number2;
		this.number3 = number3;
		this.number4 = number4;
		this.number5 = number5;
		this.number6 = number6;
		this.bonusNumber = bonusNumber;
	}

	public Integer getDrawNo() {
		return drawNo;
	}

	public LocalDate getDrawDate() {
		return drawDate;
	}

	public Integer getNumber1() {
		return number1;
	}

	public Integer getNumber2() {
		return number2;
	}

	public Integer getNumber3() {
		return number3;
	}

	public Integer getNumber4() {
		return number4;
	}

	public Integer getNumber5() {
		return number5;
	}

	public Integer getNumber6() {
		return number6;
	}

	public Integer getBonusNumber() {
		return bonusNumber;
	}
}
