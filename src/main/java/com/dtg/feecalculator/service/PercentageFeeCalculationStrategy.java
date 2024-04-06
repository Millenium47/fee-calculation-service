package com.dtg.feecalculator.service;

import com.dtg.feecalculator.dto.FeeCalculationRequest;
import com.dtg.feecalculator.model.Fee;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PercentageFeeCalculationStrategy implements FeeCalculationStrategy {

    @Override
    public BigDecimal calculateFee(FeeCalculationRequest request, Fee fee) {
        BigDecimal feePercentage = fee.getPercentageFee().divide(BigDecimal.valueOf(100));
        return request.amount().multiply(feePercentage).setScale(2, RoundingMode.HALF_EVEN);
    }
}
