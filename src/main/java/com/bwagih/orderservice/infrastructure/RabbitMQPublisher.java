package com.bwagih.orderservice.infrastructure;

import com.bwagih.orderservice.shared.Defines;
import com.bwagih.orderservice.domain.MessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQPublisher implements MessagePublisher {
    private final RabbitTemplate rabbitTemplate;


    @Override
    public void publish(String exchange, String routingKey, Object message) {
        if (message == null) {
            throw new IllegalArgumentException(Defines.MQ.INVALID_MESSAGE);
        }

        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            log.info(Defines.MQ.MESSAGE_PUBLISHED,
                    exchange, routingKey, message);
        } catch (AmqpException ex) {
            log.error(Defines.MQ.MESSAGE_PUBLISH_ERROR, exchange, routingKey, ex.getMessage(), ex);
            throw ex;
        }
    }
}
