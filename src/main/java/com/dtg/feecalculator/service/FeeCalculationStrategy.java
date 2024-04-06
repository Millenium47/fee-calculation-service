package com.dtg.feecalculator.service;

import com.dtg.feecalculator.dto.FeeCalculationRequest;
import com.dtg.feecalculator.model.Fee;

import java.math.BigDecimal;

public interface FeeCalculationStrategy {
    BigDecimal calculateFee(FeeCalculationRequest request, Fee fee);
}
