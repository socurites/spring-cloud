package com.socurites.cloud.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceCircuitBreakerConfig {
  @Bean
  public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {
    CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
      .failureRateThreshold(4)
      .waitDurationInOpenState(Duration.ofMillis(1000))
      .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
      .slidingWindowSize(2)
      .build();

    TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
      .timeoutDuration(Duration.ofSeconds(4))
      .build();

//    Customizer<Resilience4JCircuitBreakerFactory> customizer = new Customizer<>() {
//      @Override
//      public void customize(Resilience4JCircuitBreakerFactory factory) {
//        // Function<String, Resilience4JConfigBuilder.Resilience4JCircuitBreakerConfiguration> defaultConfiguration
//        factory.configureDefault((new Function<String, Resilience4JConfigBuilder.Resilience4JCircuitBreakerConfiguration>() {
//          @Override
//          public Resilience4JConfigBuilder.Resilience4JCircuitBreakerConfiguration apply(String id) {
//            return new Resilience4JConfigBuilder(id)
//              .timeLimiterConfig(timeLimiterConfig)
//              .circuitBreakerConfig(circuitBreakerConfig)
//              .build();
//          }
//        }));
//      }
//    };

    return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
      .timeLimiterConfig(timeLimiterConfig)
      .circuitBreakerConfig(circuitBreakerConfig)
      .build());
  }
}
