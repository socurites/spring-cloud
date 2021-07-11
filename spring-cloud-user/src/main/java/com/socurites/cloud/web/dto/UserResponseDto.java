package com.socurites.cloud.web.dto;

import com.socurites.cloud.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
  private String userId;
  private String email;
  private String name;
  private String password;

  public UserResponseDto(User entity) {
    this.userId = entity.getUserId();
    this.email = entity.getEmail();
    this.name = entity.getName();
    this.password = entity.getPassword();
  }
}
