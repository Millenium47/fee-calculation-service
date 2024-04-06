package com.dtg.feecalculator.repository;

import com.dtg.feecalculator.model.Fee;
import com.dtg.feecalculator.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    Optional<Fee> findByTransactionType(TransactionType transactionType);
}