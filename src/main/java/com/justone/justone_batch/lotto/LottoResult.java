package com.justone.justone_batch.lotto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lotto_result")
public class LottoResult {
	@Id
	@Column(name = "drw_no")
	private Integer drwNo;

	@Column(name = "drw_no_date", nullable = false)
	private LocalDate drwDate;

	@Column(name = "drwt_no1", nullable = false)
	private Integer number1;

	@Column(name = "drwt_no2", nullable = false)
	private Integer number2;

	@Column(name = "drwt_no3", nullable = false)
	private Integer number3;

	@Column(name = "drwt_no4", nullable = false)
	private Integer number4;

	@Column(name = "drwt_no5", nullable = false)
	private Integer number5;

	@Column(name = "drwt_no6", nullable = false)
	private Integer number6;

	@Column(name = "bnus_no", nullable = false)
	private Integer bonusNumber;

	protected LottoResult() {
	}

	public LottoResult(
		Integer drwNo,
		LocalDate drwDate,
		Integer number1,
		Integer number2,
		Integer number3,
		Integer number4,
		Integer number5,
		Integer number6,
		Integer bonusNumber
	) {
		this.drwNo = drwNo;
		this.drwDate = drwDate;
		this.number1 = number1;
		this.number2 = number2;
		this.number3 = number3;
		this.number4 = number4;
		this.number5 = number5;
		this.number6 = number6;
		this.bonusNumber = bonusNumber;
	}

	public Integer getDrwNo() {
		return drwNo;
	}

	public LocalDate getDrwDate() {
		return drwDate;
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
