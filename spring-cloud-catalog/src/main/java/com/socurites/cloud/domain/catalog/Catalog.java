package com.socurites.cloud.domain.catalog;

import com.socurites.cloud.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Catalog extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String productId;

  @Column(nullable = false, length = 512)
  private String productName;

  @Column(nullable = false)
  private Integer stock;

  @Column(nullable = false)
  private Integer unitPrice;

  public void sell(Integer qty) {
    stock -= qty;
  }
}
