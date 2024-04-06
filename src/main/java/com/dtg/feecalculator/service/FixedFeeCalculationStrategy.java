package com.dtg.feecalculator.service;

import com.dtg.feecalculator.dto.FeeCalculationRequest;
import com.dtg.feecalculator.model.Fee;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FixedFeeCalculationStrategy implements FeeCalculationStrategy {

    @Override
    public BigDecimal calculateFee(FeeCalculationRequest request, Fee fee) {
        return fee.getFixedFee();
    }
}
