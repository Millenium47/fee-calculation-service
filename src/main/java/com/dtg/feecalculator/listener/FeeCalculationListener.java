package com.dtg.feecalculator.listener;

import com.dtg.feecalculator.config.FeeCalculationRabbitMQConfig;
import com.dtg.feecalculator.dto.FeeCalculationRequest;
import com.dtg.feecalculator.service.FeeCalculationService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FeeCalculationListener {

    private final FeeCalculationService feeCalculationService;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public FeeCalculationListener(FeeCalculationService feeCalculationService, RabbitTemplate rabbitTemplate) {
        this.feeCalculationService = feeCalculationService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = FeeCalculationRabbitMQConfig.REQUEST_QUEUE)
    public void receiveMessage(FeeCalculationRequest request, @Header("correlation_id") String correlationId) {
        BigDecimal fee = feeCalculationService.calculateFee(request);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setCorrelationId(correlationId);
        Message responseMessage = new Message(fee.toString().getBytes(), messageProperties);

        rabbitTemplate.send(FeeCalculationRabbitMQConfig.RESPONSE_QUEUE, responseMessage);
    }
}
