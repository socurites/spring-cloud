package com.socurites.cloud.filter;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
  private final Environment environment;

  public AuthorizationHeaderFilter(Environment environment) {
    super(Config.class);
    this.environment = environment;
  }

  @Override
  public GatewayFilter apply(Config config) {
    return ((exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();

      if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
        return onError(exchange, "Authorization > No authorization header", HttpStatus.UNAUTHORIZED);
      }

      String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
      log.info(String.format("Authorization> authorizationHeader: %s", authorizationHeader));

      String jwt = authorizationHeader.replace("Bearer", "");

      if (!isJwtValid(jwt)) {
        return onError(exchange, "Authorization > JWT token is not valid", HttpStatus.UNAUTHORIZED)
;      }

      return chain.filter(exchange);
    });
  }

  private boolean isJwtValid(String jwt) {
    boolean valid = false;

    String subject = null;
    try {
      subject = Jwts.parser()
        .setSigningKey(environment.getProperty("token.secret"))
        .parseClaimsJws(jwt)
        .getBody()
        .getSubject();
    } catch (Exception e) {
      log.error("Authorization > JWT validation failure");
    }

    if (null == subject || subject.isEmpty()) {
      log.error("Authorization > JWT validation failure");
    } else {
      valid = true;
    }

    return valid;

  }

  private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(httpStatus);

    log.error(errorMessage);

    return response.setComplete();
  }

  public static class Config {
    // Put the configuration properties
  }
}
