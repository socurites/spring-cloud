package com.socurites.cloud.web.dto;

import com.socurites.cloud.domain.order.Order;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderSaveRequestDto {
  private String userId;
  private String productId;
  private Integer qty;
  private Integer unitPrice;

  @Builder
  public OrderSaveRequestDto(String userId, String productId, Integer qty, Integer unitPrice) {
    this.userId = userId;
    this.productId = productId;
    this.qty = qty;
    this.unitPrice = unitPrice;
  }

  public Order toEntity() {
    return Order.builder()
      .orderId(UUID.randomUUID().toString().replace("-", ""))
      .userId(userId)
      .productId(productId)
      .qty(qty)
      .unitPrice(unitPrice)
      .totalPrice(qty * unitPrice)
      .build();
  }
}
