package com.dtg.feecalculator.listener;

import com.dtg.feecalculator.config.FeeCalculationRabbitMQConfig;
import com.dtg.feecalculator.dto.FeeCalculationRequest;
import com.dtg.feecalculator.model.TransactionType;
import com.dtg.feecalculator.service.FeeCalculationService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class FeeCalculationListenerTest {

    @MockBean
    private FeeCalculationService feeCalculationService;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FeeCalculationListener feeCalculationListener;

    @Captor
    private ArgumentCaptor<Message> messageCaptor;

    @Test
    void receiveMessage_sendsCalculatedFeeResponse() {
        FeeCalculationRequest request = new FeeCalculationRequest(TransactionType.DEPOSIT, new BigDecimal("1000.00"));
        BigDecimal calculatedFee = new BigDecimal("100.00");
        String correlationId = "123e4567-e89b-12d3-a456-426614174000";

        when(feeCalculationService.calculateFee(request)).thenReturn(calculatedFee);

        feeCalculationListener.receiveMessage(request, correlationId);

        verify(rabbitTemplate).send(Mockito.eq(FeeCalculationRabbitMQConfig.RESPONSE_QUEUE), messageCaptor.capture());
        Message sentMessage = messageCaptor.getValue();
        assertEquals(calculatedFee.toString(), new String(sentMessage.getBody()));
        assertEquals(correlationId, sentMessage.getMessageProperties().getCorrelationId());
    }
}
