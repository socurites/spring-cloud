package com.socurites.cloud.domain.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findByUserId(String userId);

  Optional<User> findByEmail(String email);
}
