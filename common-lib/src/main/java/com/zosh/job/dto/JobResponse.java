package com.zosh.job.dto;

import com.zosh.job.domain.ExperienceLevel;
import com.zosh.job.domain.JobStatus;
import com.zosh.job.domain.JobType;
import com.zosh.job.domain.WorkMode;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
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
public class JobResponse {
  private Long id;
  private String title;
  private String description;
  private String requirements;
  private String responsibilities;
  private String benefits;

  private CompanyResponse company;
  private Long employerId;

  private JobCategoryResponse category;
  private Set<JobSkillResponse> skills;
  private Set<JobTagResponse> tags;

  // location
  private String address;
  private String city;
  private String state;
  private String country;
  private String zipCode;

  // Salary
  private BigDecimal minSalary;
  private BigDecimal maxSalary;

  // Classification
  private JobType jobType;
  private WorkMode workMode;
  private ExperienceLevel experienceLevel;
  private JobStatus jobStatus;

  // posting details
  private Integer openings;
  private LocalDate applicationDeadLine;
  private LocalDate expiresAt;
  private Boolean active;

  // timestamps
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime publishedAt;
  private LocalDateTime closedAt;

  private Integer applicationCount;
}
