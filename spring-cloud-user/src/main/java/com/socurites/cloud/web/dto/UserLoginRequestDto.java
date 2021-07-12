package com.socurites.cloud.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UserLoginRequestDto {
  @NotNull(message = "Email can not be null")
  @Size(min = 2, message = "email can not be less than 2 characters")
  @Email
  private String email;

  @NotNull(message = "Password can not be null")
  @Size(min = 2, max = 16, message = "Password should be 2 to 16 characters")
  private String password;
}
