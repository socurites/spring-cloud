package com.socurites.cloud.client;

import com.socurites.cloud.web.dto.OrderResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("ORDER-SERVICE")
public interface OrderServiceClient {
  @GetMapping("/orders/{userId}")
  List<OrderResponseDto> findByUserId(@PathVariable String userId);

  @GetMapping("/orders-no-route/{userId}")
  List<OrderResponseDto> findFalseByUserId(@PathVariable String userId);
}
