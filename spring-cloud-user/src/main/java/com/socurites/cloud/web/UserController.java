package com.socurites.cloud.web;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.socurites.cloud.service.UserService;
import com.socurites.cloud.web.dto.UserOrderResponseDto;
import com.socurites.cloud.web.dto.UserResponseDto;
import com.socurites.cloud.web.dto.UserSaveRequestDto;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
  private final Environment environment;
  private final UserService userService;

  @GetMapping("/users/test")
  public String test(@RequestHeader("req-header-key") String header) {
    log.info(header);
    
    return "test";
  }

  @GetMapping("/users/custom")
  public String test() {
    log.info("custom");
    return "custom";
  }

  ////////////////////////////////////////////////////////////////////////////

  @GetMapping("/users/health-check")
  @Timed(value="users.status", longTask = true)
  public String status() {
    return String.format("It's working in user-service on port(local.server.port): %s\n"
      + "port(server.port): %s\n"
      + "token secret: %s\n"
      + "token expiration time: %s",
      environment.getProperty("local.server.port"),
      environment.getProperty("server.port"),
      environment.getProperty("token.secret"),
      environment.getProperty("token.expiration-time"));
  }

  @GetMapping("/users/welcome")
  @Timed(value="users.welcome", longTask = true)
  public String welcome() {
    return environment.getProperty("greeting.message");
  }

  @PostMapping("/users")
  public ResponseEntity<UserResponseDto> save(@RequestBody UserSaveRequestDto requestDto) {
    UserResponseDto responseDto = userService.save(requestDto);

    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(responseDto);
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserOrderResponseDto>> findAll() {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(userService.findAll());
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<UserOrderResponseDto> findByUserId(@PathVariable String userId) {
    return ResponseEntity
      .status(HttpStatus.OK)
      .body(userService.findByUserId(userId));
  }
}
