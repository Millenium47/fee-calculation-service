package com.dtg.feecalculator.service;

import com.dtg.feecalculator.dto.FeeCalculationRequest;
import com.dtg.feecalculator.exception.NotFoundException;
import com.dtg.feecalculator.exception.UnsupportedTransactionTypeException;
import com.dtg.feecalculator.model.Fee;
import com.dtg.feecalculator.model.TransactionType;
import com.dtg.feecalculator.repository.FeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeeCalculationServiceTest {

    @Mock
    private FeeRepository feeRepository;

    @Mock
    private Map<TransactionType, FeeCalculationStrategy> algorithms;

    @InjectMocks
    private FeeCalculationService service;

    private FeeCalculationRequest request;

    private Fee fee;

    @BeforeEach
    void setUp() {
        fee = new Fee(1L, TransactionType.DEPOSIT, new BigDecimal("5.00"), null);
        request = new FeeCalculationRequest(TransactionType.DEPOSIT, new BigDecimal("100.00"));
    }

    @Test
    void testCalculateFee_Success() {
        when(feeRepository.findByTransactionType(TransactionType.DEPOSIT)).thenReturn(Optional.of(fee));
        when(algorithms.get(TransactionType.DEPOSIT)).thenReturn((req, amount) -> new BigDecimal("5.00"));

        BigDecimal result = service.calculateFee(request);

        assertEquals(new BigDecimal("5.00"), result);
        verify(feeRepository).findByTransactionType(TransactionType.DEPOSIT);
        verify(algorithms).get(TransactionType.DEPOSIT);
    }

    @Test
    void testCalculateFee_NotFoundException() {
        when(feeRepository.findByTransactionType(TransactionType.DEPOSIT)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.calculateFee(request));
    }

    @Test
    void testCalculateFee_UnsupportedTransactionTypeException() {
        when(feeRepository.findByTransactionType(TransactionType.DEPOSIT)).thenReturn(Optional.of(fee));
        when(algorithms.get(TransactionType.DEPOSIT)).thenReturn(null);

        assertThrows(UnsupportedTransactionTypeException.class, () -> service.calculateFee(request));
    }

}
