package com.dtg.feecalculator.service;

import com.dtg.feecalculator.dto.FeeCalculationRequest;
import com.dtg.feecalculator.model.Fee;
import com.dtg.feecalculator.model.TransactionType;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FixedFeeCalculationStrategyTest {

    @Test
    void calculateFee_ShouldReturnFixedFee() {
        FixedFeeCalculationStrategy strategy = new FixedFeeCalculationStrategy();
        FeeCalculationRequest request = new FeeCalculationRequest(TransactionType.DEPOSIT, new BigDecimal("100.00"));
        Fee fee = new Fee(1L, TransactionType.DEPOSIT, new BigDecimal("5.00"), null);

        BigDecimal result = strategy.calculateFee(request, fee);

        assertEquals(fee.getFixedFee(), result);
    }
}
