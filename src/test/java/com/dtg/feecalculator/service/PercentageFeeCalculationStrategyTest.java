package com.dtg.feecalculator.service;

import com.dtg.feecalculator.dto.FeeCalculationRequest;
import com.dtg.feecalculator.model.Fee;
import com.dtg.feecalculator.model.TransactionType;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PercentageFeeCalculationStrategyTest {

    @Test
    void calculateFee_ShouldReturnPercentageOfAmount() {
        FeeCalculationRequest request = new FeeCalculationRequest(TransactionType.TRANSFER, new BigDecimal("1000.00"));
        Fee fee = new Fee(1L, TransactionType.DEPOSIT, null, new BigDecimal("15.00"));
        PercentageFeeCalculationStrategy strategy = new PercentageFeeCalculationStrategy();

        BigDecimal result = strategy.calculateFee(request, fee);

        assertEquals(new BigDecimal("150.00"), result);
    }
}
