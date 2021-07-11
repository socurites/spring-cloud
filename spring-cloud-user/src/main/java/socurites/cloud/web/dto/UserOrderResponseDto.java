package socurites.cloud.web.dto;

import lombok.Getter;
import socurites.cloud.domain.user.User;

import java.util.List;

@Getter
public class UserOrderResponseDto {
  private String userId;
  private String email;
  private String name;
  private List<OrderResponseDto> orders;

  public UserOrderResponseDto(User entity) {
    this.userId = entity.getUserId();
    this.email = entity.getEmail();
    this.name = entity.getName();
  }
}
