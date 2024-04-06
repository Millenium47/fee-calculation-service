package com.dtg.feecalculator.dto;

import com.dtg.feecalculator.model.TransactionType;

import java.math.BigDecimal;

public record FeeCalculationRequest(TransactionType transactionType, BigDecimal amount) {
}
