package com.ezo.defi.calculfacile.service;

import com.ezo.defi.calculfacile.model.CalculationRequest;
import com.ezo.defi.calculfacile.model.CheckCalculation;
import com.ezo.defi.calculfacile.model.ResponseToCalculation;


public interface CalculService {

        CalculationRequest createACalculation(String remoteAddr);

        CheckCalculation checkResponse(ResponseToCalculation responseToCalculation, String remoteAddr);

        String getAllResult();

}
