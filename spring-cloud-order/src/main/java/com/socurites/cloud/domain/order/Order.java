package com.socurites.cloud.domain.order;

import com.socurites.cloud.domain.BaseEntity;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "\"order\"")
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String orderId;

  private String userId;

  private String productId;

  private Integer qty;

  private Integer unitPrice;

  private Integer totalPrice;

  @Builder
  public Order(String orderId, String userId, String productId, Integer qty, Integer unitPrice, Integer totalPrice) {
    this.orderId = orderId;
    this.userId = userId;
    this.productId = productId;
    this.qty = qty;
    this.unitPrice = unitPrice;
    this.totalPrice = totalPrice;
  }
}
