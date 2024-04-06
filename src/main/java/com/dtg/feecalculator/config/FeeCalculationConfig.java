package com.dtg.feecalculator.config;

import com.dtg.feecalculator.model.TransactionType;
import com.dtg.feecalculator.service.FeeCalculationStrategy;
import com.dtg.feecalculator.service.FixedFeeCalculationStrategy;
import com.dtg.feecalculator.service.PercentageFeeCalculationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FeeCalculationConfig {

    @Bean
    public Map<TransactionType, FeeCalculationStrategy> feeCalculationStrategyMap(
            PercentageFeeCalculationStrategy percentageStrategy,
            FixedFeeCalculationStrategy fixedStrategy) {

        Map<TransactionType, FeeCalculationStrategy> strategies = new HashMap<>();

        strategies.put(TransactionType.TRANSFER, percentageStrategy);
        strategies.put(TransactionType.DEPOSIT, fixedStrategy);
        strategies.put(TransactionType.WITHDRAWAL, fixedStrategy);

        return strategies;
    }
}
