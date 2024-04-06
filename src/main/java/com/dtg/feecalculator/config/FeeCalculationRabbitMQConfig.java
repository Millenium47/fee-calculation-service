package com.dtg.feecalculator.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeeCalculationRabbitMQConfig {

    public static final String REQUEST_QUEUE = "fee_calculation_request_queue";
    public static final String REQUEST_EXCHANGE = "fee_calculation_request_exchange";
    public static final String REQUEST_ROUTING_KEY = "fee.calculation.request.key";

    public static final String RESPONSE_QUEUE = "fee_calculation_response_queue";
    public static final String RESPONSE_EXCHANGE = "fee_calculation_response_exchange";
    public static final String RESPONSE_ROUTING_KEY = "fee.calculation.response.key";

    @Bean
    Queue requestQueue() {
        return new Queue(REQUEST_QUEUE, false);
    }

    @Bean
    TopicExchange requestExchange() {
        return new TopicExchange(REQUEST_EXCHANGE);
    }

    @Bean
    Binding requestBinding(Queue requestQueue, TopicExchange requestExchange) {
        return BindingBuilder.bind(requestQueue).to(requestExchange).with(REQUEST_ROUTING_KEY);
    }

    @Bean
    Queue responseQueue() {
        return new Queue(RESPONSE_QUEUE, false);
    }

    @Bean
    TopicExchange responseExchange() {
        return new TopicExchange(RESPONSE_EXCHANGE);
    }

    @Bean
    Binding responseBinding(Queue responseQueue, TopicExchange responseExchange) {
        return BindingBuilder.bind(responseQueue).to(responseExchange).with(RESPONSE_ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
