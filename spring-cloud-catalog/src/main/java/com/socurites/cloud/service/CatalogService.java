package com.socurites.cloud.service;

import com.socurites.cloud.domain.catalog.CatalogRepository;
import com.socurites.cloud.web.dto.CatalogResponseDto;
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
}
