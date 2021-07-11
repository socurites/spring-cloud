package socurites.cloud.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import socurites.cloud.domain.user.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {
  @NotNull(message = "Email can not be null")
  @Size(min = 2, message = "email can not be less than 2 characters")
  @Email
  private String email;
  private String name;
  private String password;

  @Builder
  public UserSaveRequestDto(String email, String name, String password) {
    this.email = email;
    this.name = name;
    this.password = password;
  }

  public User toEntity(PasswordEncoder passwordEncoder) {
    return User.builder()
      .userId(UUID.randomUUID().toString().replace("-", ""))
      .email(email)
      .name(name)
      .password(passwordEncoder.encode(password))
      .build();
  }
}
