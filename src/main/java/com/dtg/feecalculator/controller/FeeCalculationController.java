package com.dtg.feecalculator.controller;

import com.dtg.feecalculator.dto.FeeCalculationRequest;
import com.dtg.feecalculator.service.FeeCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/fees")
public class FeeCalculationController {

    private final FeeCalculationService feeCalculationService;

    @Autowired
    public FeeCalculationController(FeeCalculationService feeCalculationService) {
        this.feeCalculationService = feeCalculationService;
    }

    // POST is used because we are handling financial data, and we don't want them to be exposed in url with GET
    @PostMapping("/calculate")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal calculateFee(@RequestBody FeeCalculationRequest request) {
        return feeCalculationService.calculateFee(request);
    }
}
