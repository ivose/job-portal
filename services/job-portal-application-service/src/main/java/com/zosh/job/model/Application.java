package com.zosh.job.model;

import com.zosh.job.db.BaseTable;
import com.zosh.job.domain.AishortListStatus;
import com.zosh.job.domain.ApplicationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "applications")
public class Application extends BaseTable {

  @Column(nullable = false)
  private Long candidateId;

  @Column(nullable = false)
  private Long jobId;

  @Column(nullable = false)
  private Long companyId;

  @Column(nullable = false)
  private Long employerId;

  @Column(nullable = false)
  private Long resumeId;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ApplicationStatus status = ApplicationStatus.PENDING;

  private String coverLetter;

  private BigDecimal expectedSalary;

  private LocalDate availableFrom;

  @Builder.Default private Boolean isRead = false;

  @Builder.Default private Boolean isStarred = false;

  @Column private Integer aiScore;

  @Enumerated(EnumType.STRING)
  private AishortListStatus aishortListStatus;

  private LocalDateTime withdrawnAt;
  private LocalDateTime appliedAt;

  private String withrawnReason;
}
