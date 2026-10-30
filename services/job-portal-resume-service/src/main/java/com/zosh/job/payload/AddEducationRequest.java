package com.zosh.job.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddEducationRequest {
  private Long id;

  @NotBlank(message = "Institution name is required")
  private String institutionName;

  @NotBlank(message = "Degree is required")
  private String degree;

  private String fieldOfStudy;

  private String grade;

  @NotBlank(message = "Start date is required")
  private String startDate;

  private String endDate;

  private Boolean isCurrentEducation = false;

  private String description;

  private Integer displayOrder;
}
