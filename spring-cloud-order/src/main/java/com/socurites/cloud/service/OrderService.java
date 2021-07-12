package com.socurites.cloud.service;

import com.socurites.cloud.domain.order.Order;
import com.socurites.cloud.domain.order.OrderRepository;
import com.socurites.cloud.web.dto.OrderResponseDto;
import com.socurites.cloud.web.dto.OrderSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;

  @Transactional
  public OrderResponseDto save(OrderSaveRequestDto requestDto) {
    Order saved = orderRepository.save(requestDto.toEntity());
    return new OrderResponseDto(saved);
  }

  @Transactional(readOnly = true)
  public List<OrderResponseDto> findByUserId(String userId) {
    return orderRepository.findByUserId(userId).stream()
      .map(OrderResponseDto::new)
      .collect(Collectors.toList());
  }
  
  @Transactional(readOnly = true)
  public OrderResponseDto findByOrderId(String orderId) {
    Order order = orderRepository.findByOrderId(orderId)
      .orElseThrow(() ->
        new IllegalArgumentException(String.format("No order for orderId: %s", orderId)));

    return new OrderResponseDto(order);
  }
}
