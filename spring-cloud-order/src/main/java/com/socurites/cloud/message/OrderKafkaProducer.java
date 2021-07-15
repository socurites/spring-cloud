package com.socurites.cloud.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.socurites.cloud.web.dto.OrderResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderKafkaProducer {
  private final String topicName;
  private final KafkaTemplate<String, String> kafkaTemplate;

  public OrderKafkaProducer(@Value("${app.kafka.topic}") String topicName,
                         KafkaTemplate<String, String> kafkaTemplate) {
    this.topicName = topicName;
    this.kafkaTemplate = kafkaTemplate;
  }

  public void produce(OrderResponseDto orderDto) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    try {
      String message = mapper.writeValueAsString(orderDto);
      send(message);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(String.format("Error while serializing order: %s",
        orderDto.getOrderId()), e);
    }
  }

  private void send(String message) {
    log.info(String.format("Sending %s messages", message));

    kafkaTemplate.send(topicName, message)
      .addCallback(new KafkaSendCallback<String, String>() {
        @Override
        public void onSuccess(SendResult<String, String> result) {
          RecordMetadata recordMetadata = result.getRecordMetadata();
          log.info("Produced record to topic {}, partition {}, @ offset {}",
            recordMetadata.topic(),
            recordMetadata.partition(),
            recordMetadata.offset());
        }

        @Override
        public void onFailure(Throwable ex) {
          log.error(ex.getMessage(), ex);
          KafkaSendCallback.super.onFailure(ex);
        }

        @Override
        public void onFailure(KafkaProducerException ex) {
          log.error(ex.getMessage(), ex);
        }
      });
  }
}
