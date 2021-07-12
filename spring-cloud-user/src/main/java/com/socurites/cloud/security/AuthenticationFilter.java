package com.socurites.cloud.security;

import com.socurites.cloud.service.UserService;
import com.socurites.cloud.web.dto.UserResponseDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private final UserService userService;
  private final Environment environment;

  private static final int DEFAULT_TOKEN_VALID_SEC = 1800;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
                                              HttpServletResponse response) throws AuthenticationException {
    log.info(String.format("Try to login by %s", request.getParameter(getUsernameParameter())));
    return super.attemptAuthentication(request, response);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) throws IOException, ServletException {
    User principal = (User)authResult.getPrincipal();
    log.info(String.format("Successful login by %s", principal.getUsername()));
    log.info(String.format("JWT valid until %s", getExpirationDate()));

    UserResponseDto userResponseDto = userService.findByEmail(principal.getUsername());


    String token = Jwts.builder()
      .setSubject(userResponseDto.getUserId())
      .setExpiration(getExpirationDate())
      .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
      .compact();

    response.addHeader("token", token);
    response.addHeader("userId", userResponseDto.getUserId());

//    super.successfulAuthentication(request, response, chain, authResult);
  }

  private Date getExpirationDate() {
    LocalDateTime expirationDateTime = LocalDateTime.now().plusSeconds(
      environment.getProperty("token.expiration-time", Integer.class, DEFAULT_TOKEN_VALID_SEC));

    return Date.from(expirationDateTime.toInstant(ZoneOffset.of("+09:00")));
  }
}
