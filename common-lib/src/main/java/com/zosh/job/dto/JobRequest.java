package com.zosh.job.dto;

import com.zosh.job.domain.ExperienceLevel;
import com.zosh.job.domain.JobType;
import com.zosh.job.domain.WorkMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRequest {
  @NotBlank(message = "job title cannot be blank")
  private String title;

  @NotBlank(message = "job description cannot be blank")
  private String description;

  private String requirements;
  private String responsibilities;
  private String benefits;

  @NotNull(message = "Category is required")
  private Long categoryId;

  private Set<Long> skillIds;

  private Set<Long> tagIds;

  private Integer openings;

  private String address;
  private String city;
  private String state;
  private String country;
  private String zipCode;

  @NotNull(message = "Application deadline is required")
  @FutureOrPresent(message = "Application deadline cannot be in the past")
  private LocalDate applicationDeadLine;

  @NotNull(message = "Expired at is required")
  private LocalDate expiredAt;

  @DecimalMin(value = "0.0", inclusive = true, message = "Minimum salary cannot be negative")
  private BigDecimal minSalary;

  @DecimalMin(value = "0.0", inclusive = true, message = "Maximum salary cannot be negative")
  private BigDecimal maxSalary;

  @NotNull(message = "Job type is required")
  private JobType jobType;

  @NotNull(message = "Work mode is required")
  private WorkMode workMode;

  @NotNull(message = "Experience level is required")
  private ExperienceLevel experienceLevel;
}
