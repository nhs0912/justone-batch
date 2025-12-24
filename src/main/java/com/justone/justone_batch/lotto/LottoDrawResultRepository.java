package com.justone.justone_batch.lotto;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LottoDrawResultRepository extends JpaRepository<LottoDrawResult, Integer> {
	Optional<LottoDrawResult> findTopByOrderByDrawNoDesc();
}
