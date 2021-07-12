package com.socurites.cloud.service;

import com.socurites.cloud.domain.user.User;
import com.socurites.cloud.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.socurites.cloud.web.dto.UserOrderResponseDto;
import com.socurites.cloud.web.dto.UserResponseDto;
import com.socurites.cloud.web.dto.UserSaveRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

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

    // TODO: set orders

    return new UserOrderResponseDto(user);
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
