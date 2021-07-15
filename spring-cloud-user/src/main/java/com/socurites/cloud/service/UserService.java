package com.socurites.cloud.service;

import com.socurites.cloud.domain.user.User;
import com.socurites.cloud.domain.user.UserRepository;
import com.socurites.cloud.client.OrderServiceClient;
import com.socurites.cloud.web.dto.OrderResponseDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.socurites.cloud.web.dto.UserOrderResponseDto;
import com.socurites.cloud.web.dto.UserResponseDto;
import com.socurites.cloud.web.dto.UserSaveRequestDto;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final Environment environment;
  private final RestTemplate restTemplate;
  private final OrderServiceClient orderServiceClient;
  private final CircuitBreakerFactory circuitBreakerFactory;

  @Transactional
  public UserResponseDto save(UserSaveRequestDto requestDto) {
    User saved = userRepository.save(requestDto.toEntity(passwordEncoder));
    return new UserResponseDto(saved);
  }

  @Transactional(readOnly = true)
  public UserOrderResponseDto findByUserId(String userId) {
    User user = userRepository.findByUserId(userId)
      .orElseThrow(() ->
        new IllegalArgumentException(String.format("No user for userId: %s", userId)));

    // using restTemplate + properties
//    String orderUrl = String.format("http://%s:%s",
//      environment.getProperty("gateway.ip"), environment.getProperty("gateway.port")) +
//      String.format(environment.getProperty("service.order-service.uri.get-orders"), userId);

    // using restTemplate + eureka
//    String orderUrl = String.format("http://ORDER-SERVICE") +
//      String.format(environment.getProperty("service.order-service.uri.get-orders"), userId);
//    log.info(String.format("Rest call to %s", orderUrl));
//    ResponseEntity<List<OrderResponseDto>> orderListResponse = restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//      new ParameterizedTypeReference<>() {});
//    UserOrderResponseDto userOrderResponseDto = new UserOrderResponseDto(user);
//    userOrderResponseDto.addOrders(orderListResponse.getBody());

    // FeignClient: Before ErrorDecoder
//    List<OrderResponseDto> orderList = null;
//    try {
//      orderList = orderServiceClient.findFalseByUserId(userId);
//    } catch (FeignException e) {
//      log.error(e.getMessage());
//    }

    // FeignClient: With ErrorDecoder
//    List<OrderResponseDto> orderList = orderServiceClient.findFalseByUserId(userId);

    // FeignClient + CircuittBreaker
    CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");
    List<OrderResponseDto> orderList = circuitBreaker.run(
      () -> orderServiceClient.findByUserId(userId),
      throwable -> new ArrayList<>());

    UserOrderResponseDto userOrderResponseDto = new UserOrderResponseDto(user);
    userOrderResponseDto.addOrders(orderList);

    return userOrderResponseDto;
  }

  @Transactional(readOnly = true)
  public List<UserOrderResponseDto> findAll() {
    // TODO: set orders

    return StreamSupport.stream(userRepository.findAll().spliterator(), false)
      .map(UserOrderResponseDto::new)
      .collect(Collectors.toList());
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(username)
      .orElseThrow(() ->
        new UsernameNotFoundException(String.format("User %s not found", username)));

    return new org.springframework.security.core.userdetails.User(
      user.getEmail(),
      user.getPassword(),
      new ArrayList<>());
  }

  public UserResponseDto findByEmail(String username) {
    User user = userRepository.findByEmail(username)
      .orElseThrow(() ->
        new UsernameNotFoundException(String.format("User %s not found", username)));
    
    return new UserResponseDto(user);
  }
}
