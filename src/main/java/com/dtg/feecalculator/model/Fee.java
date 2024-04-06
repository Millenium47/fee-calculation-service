package com.dtg.feecalculator.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSACTION_TYPE")
    private TransactionType transactionType;

    @Column(name = "FIXED_FEE")
    private BigDecimal fixedFee;

    @Column(name = "PERCENTAGE_FEE")
    private BigDecimal percentageFee;

    public Fee(){}

    public Fee(Long id, TransactionType transactionType, BigDecimal fixedFee, BigDecimal percentageFee) {
        this.id = id;
        this.transactionType = transactionType;
        this.fixedFee = fixedFee;
        this.percentageFee = percentageFee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getFixedFee() {
        return fixedFee;
    }

    public void setFixedFee(BigDecimal fixedFee) {
        this.fixedFee = fixedFee;
    }

    public BigDecimal getPercentageFee() {
        return percentageFee;
    }

    public void setPercentageFee(BigDecimal percentageFee) {
        this.percentageFee = percentageFee;
    }
}
