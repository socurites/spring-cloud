package com.socurites.cloud.service;

import com.socurites.cloud.domain.catalog.Catalog;
import com.socurites.cloud.domain.catalog.CatalogRepository;
import com.socurites.cloud.web.dto.CatalogResponseDto;
import com.socurites.cloud.web.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CatalogService {
  private final CatalogRepository catalogRepository;

  @Transactional(readOnly = true)
  public List<CatalogResponseDto> findAll() {
    return StreamSupport.stream(catalogRepository.findAll().spliterator(), false)
      .map(CatalogResponseDto::new)
      .collect(Collectors.toList());
  }

  @Transactional
  public CatalogResponseDto sell(OrderResponseDto orderDto) {
    Catalog catalog = catalogRepository.findByProductId(orderDto.getProductId())
      .orElseThrow(() -> {
        throw new IllegalArgumentException(String.format("No catalog for productId: %s", orderDto.getProductId()));
      });

    catalog.sell(orderDto.getQty());

    return new CatalogResponseDto(catalog);
  }
}
