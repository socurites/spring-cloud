package com.socurites.cloud.web;

import com.socurites.cloud.service.OrderService;
import com.socurites.cloud.web.dto.OrderResponseDto;
import com.socurites.cloud.web.dto.OrderSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class OrderController {
  private final Environment environment;
  private final OrderService orderService;

  @GetMapping("/orders/health-check")
  public String status() {
    return String.format("It's working in order-service on port: %d",
      environment.getProperty("local.server.port"));
  }

  @PostMapping("/orders/{userId}")
  public ResponseEntity<OrderResponseDto> save(@PathVariable("userId") String userId,
                                               @RequestBody OrderSaveRequestDto requestDto) {
    // TODO: userId는 호출하는 쪽에서 셋팅했다고 가정
    // TODO: 세션에서 가져오거나

    OrderResponseDto responseDto = orderService.save(requestDto);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(responseDto);
  }

  @GetMapping("/orders/{userId}")
  public ResponseEntity<List<OrderResponseDto>> findByUserId(@PathVariable("userId") String userId) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(orderService.findByUserId(userId));
  }
}
