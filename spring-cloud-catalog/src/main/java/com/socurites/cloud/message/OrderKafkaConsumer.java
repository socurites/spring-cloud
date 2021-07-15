package com.socurites.cloud.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.socurites.cloud.service.CatalogService;
import com.socurites.cloud.web.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderKafkaConsumer {
  private final CatalogService catalogService;

  @KafkaListener(topics = "#{'${app.kafka.topic}'}", concurrency = "1")
  public void consume(final String message) {
    log.info("Received message: {}", message);
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());

    try {
      OrderResponseDto orderResponseDto = mapper.readValue(message, new TypeReference<OrderResponseDto>() {});

      catalogService.sell(orderResponseDto);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
