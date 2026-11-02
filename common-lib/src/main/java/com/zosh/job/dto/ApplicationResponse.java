package com.zosh.job.dto;

import com.zosh.job.domain.AishortListStatus;
import com.zosh.job.domain.ApplicationStatus;
import com.zosh.job.dto.response.UserResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationResponse {
  private Long id;
  private Long employerId;
  private UserResponse candidate;
  private Long candidateId;
  private JobResponse job;
  private CompanyResponse company;
  private ApplicationStatus status;
  private Long resumeId;
  private String coverLetter;
  private BigDecimal expectedSalary;
  private LocalDate availableFrom;
  private Boolean isRead;
  private Boolean isStarred;
  private Integer aiScore;
  private AishortListStatus aishortListStatus;
  private LocalDateTime withdrawnAt;
  private String withrawnReason;
  private LocalDateTime appliedAt;
  private LocalDateTime updatedAt;
}
