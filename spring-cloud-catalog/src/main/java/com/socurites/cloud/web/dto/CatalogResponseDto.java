package com.socurites.cloud.web.dto;

import com.socurites.cloud.domain.catalog.Catalog;
import lombok.Getter;

@Getter
public class CatalogResponseDto {
  private String productId;
  private String productName;
  private Integer stock;
  private Integer unitPrice;

  public CatalogResponseDto(Catalog entity) {
    this.productId = entity.getProductId();
    this.productName = entity.getProductName();
    this.stock = entity.getStock();
    this.unitPrice = entity.getUnitPrice();
  }
}
