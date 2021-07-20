package com.socurites.cloud.security;

import com.socurites.cloud.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private static final String USERNAME_PARAMETER = "email";
  private static final String PASSWORD_PARAMETER = "password";

  @Value("${gateway.ip}")
  private String gatewayIp;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final Environment environment;

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.csrf().disable()
//      .authorizeRequests()
//        .antMatchers("/users/**")
//          .permitAll();
//  }

  @PostConstruct
  public void init() {
    log.info("Gateway IP: " + gatewayIp);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();

    http.authorizeRequests()
      .antMatchers("/actuator/**")
        .permitAll();

    http.authorizeRequests()
        .antMatchers("/**")
          .hasIpAddress(gatewayIp)   // API G/W IP
          .and()
          .addFilter(getAuthenticationFilter());

  }

  private AuthenticationFilter getAuthenticationFilter() throws Exception {
    AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService, environment);
    authenticationFilter.setAuthenticationManager(authenticationManager());
    authenticationFilter.setUsernameParameter(USERNAME_PARAMETER);
    authenticationFilter.setPasswordParameter(PASSWORD_PARAMETER);
    return authenticationFilter;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
      .userDetailsService(userService)
      .passwordEncoder(passwordEncoder);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
      .ignoring()
      .antMatchers("/h2-console/**");
  }
}
