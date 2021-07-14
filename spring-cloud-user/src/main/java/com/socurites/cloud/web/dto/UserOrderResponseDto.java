package com.socurites.cloud.web.dto;

import com.socurites.cloud.domain.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserOrderResponseDto {
  private String userId;
  private String email;
  private String name;
  private List<OrderResponseDto> orders;

  public UserOrderResponseDto(User entity) {
    this.userId = entity.getUserId();
    this.email = entity.getEmail();
    this.name = entity.getName();
  }

  public void addOrders(List<OrderResponseDto> orders) {
    if (null != orders) {
      if (null == this.orders) {
        this.orders = new ArrayList<>();
      }

      this.orders.addAll(orders);
    }
  }
}
