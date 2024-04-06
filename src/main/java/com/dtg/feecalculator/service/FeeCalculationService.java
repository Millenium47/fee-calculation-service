package com.dtg.feecalculator.service;

import com.dtg.feecalculator.dto.FeeCalculationRequest;
import com.dtg.feecalculator.exception.NotFoundException;
import com.dtg.feecalculator.exception.UnsupportedTransactionTypeException;
import com.dtg.feecalculator.model.Fee;
import com.dtg.feecalculator.model.TransactionType;
import com.dtg.feecalculator.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class FeeCalculationService {

    private final FeeRepository feeRepository;
    private final Map<TransactionType, FeeCalculationStrategy> algorithms;

    @Autowired
    public FeeCalculationService(FeeRepository feeRepository, Map<TransactionType, FeeCalculationStrategy> algorithms) {
        this.feeRepository = feeRepository;
        this.algorithms = algorithms;
    }

    public BigDecimal calculateFee(FeeCalculationRequest request){
        Fee fee = feeRepository.findByTransactionType(request.transactionType()).orElseThrow(()-> new NotFoundException("Transaction type not found!"));
        FeeCalculationStrategy algorithm = algorithms.get(request.transactionType());

        if (algorithm == null){
            throw new UnsupportedTransactionTypeException("Internal Configuration Error: No strategy found for the transaction type '" + request.transactionType() + "'.");
        }

        return algorithm.calculateFee(request, fee);
    }
}
