package com.zosh.job.payload;

import com.zosh.job.domain.AishortListStatus;
import com.zosh.job.domain.ApplicationStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateApplicationRequest {

  @NotNull(message = "Job id is required")
  private Long jobId;

  @NotNull(message = "Resume id is required")
  private Long resumeId;

  @Size(max = 3000, message = "Cover letter cannot exceed 3000 characters")
  private String coverLetter;

  @DecimalMin(value = "0.0", inclusive = true, message = "Expected salary cannot be negative")
  private BigDecimal expectedSalary;

  private LocalDate availableFrom;
  private AishortListStatus aishortListStatus;
  private ApplicationStatus status;
}
