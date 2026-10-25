package com.zosh.job.model;

import com.zosh.job.db.BaseTable;
import com.zosh.job.domain.UserRole;
import com.zosh.job.domain.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "users")
public class User extends BaseTable {

  @Column(nullable = false)
  private String fullName;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String email;

  private String phone;

  private String profileImage;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role = UserRole.ROLE_JOB_SEEKER;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserStatus status = UserStatus.ACTIVE;

  private LocalDateTime lastLogin;

  private LocalDateTime suspendedAt;

  private LocalDateTime deletedAt;
}
