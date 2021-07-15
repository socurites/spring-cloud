package com.socurites.cloud.web.dto;

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
}
