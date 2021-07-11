package com.socurites.cloud.domain.user;

import com.socurites.cloud.domain.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String userId;

  private String email;

  private String name;

  private String password;

  @Builder
  public User(String userId, String email, String name, String password) {
    this.userId = userId;
    this.email = email;
    this.name = name;
    this.password = password;
  }
}
