package com.socurites.cloud.web;

import com.socurites.cloud.service.CatalogService;
import com.socurites.cloud.web.dto.CatalogResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class CatalogController {
  private final Environment environment;
  private final CatalogService catalogService;

  @GetMapping("/catalogs/health-check")
  public String status() {
    return String.format("It's working in user-service on port: %d",
      environment.getProperty("local.server.port"));
  }

  @GetMapping("/catalogs")
  public ResponseEntity<List<CatalogResponseDto>> findAll() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(catalogService.findAll());
  }
}

