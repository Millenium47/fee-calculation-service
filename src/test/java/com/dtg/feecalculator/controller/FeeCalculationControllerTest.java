package com.dtg.feecalculator.controller;

import com.dtg.feecalculator.dto.FeeCalculationRequest;
import com.dtg.feecalculator.model.TransactionType;
import com.dtg.feecalculator.service.FeeCalculationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeeCalculationController.class)
public class FeeCalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeeCalculationService feeCalculationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCalculateFee() throws Exception {
        FeeCalculationRequest request = new FeeCalculationRequest(TransactionType.DEPOSIT, new BigDecimal("100.0"));
        BigDecimal expectedFee = new BigDecimal("100.00");

        when(feeCalculationService.calculateFee(request)).thenReturn(expectedFee);

        mockMvc.perform(post("/api/v1/fees/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedFee.toString()));
    }
}
