package com.socurites.cloud.web.dto;

import com.socurites.cloud.domain.order.Order;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponseDto {
  private String orderId;
  private String userId;
  private String productId;
  private Integer qty;
  private Integer unitPrice;
  private Integer totalPrice;
  private LocalDateTime createdDate;

  public OrderResponseDto(Order entity) {
    this.orderId = entity.getOrderId();
    this.userId = entity.getUserId();
    this.productId = entity.getProductId();
    this.qty = entity.getQty();
    this.unitPrice = entity.getUnitPrice();
    this.totalPrice = entity.getTotalPrice();
    this.createdDate = entity.getCreatedDate();
  }
}
