package com.ezo.defi.calculfacile.repository;

import com.ezo.defi.calculfacile.domain.Calculation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CalculRepository extends CrudRepository<Calculation,UUID> {
        Calculation findByUuid(UUID uuid);
        List<Calculation> findByIp(String remoteAddr);

}
