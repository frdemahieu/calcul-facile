package com.ezo.defi.calculfacile.repository;

import com.ezo.defi.calculfacile.domain.Calculation;
import com.ezo.defi.calculfacile.domain.Result;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ResultRepository extends CrudRepository<Result,UUID> {
        Result findResultByIp(String ip);
        List<Result> findAllByOrderByResponseTimeAsc();
}
