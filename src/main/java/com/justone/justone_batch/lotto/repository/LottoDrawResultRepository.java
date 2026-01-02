package com.justone.justone_batch.lotto.repository;

import com.justone.justone_batch.lotto.entity.LottoResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LottoDrawResultRepository extends JpaRepository<LottoResult, Integer> {

    List<LottoResult> findAllById(Iterable<Integer> ids);

}
