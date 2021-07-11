package socurites.cloud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import socurites.cloud.domain.user.User;
import socurites.cloud.domain.user.UserRepository;
import socurites.cloud.web.dto.UserOrderResponseDto;
import socurites.cloud.web.dto.UserResponseDto;
import socurites.cloud.web.dto.UserSaveRequestDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService {
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
}
