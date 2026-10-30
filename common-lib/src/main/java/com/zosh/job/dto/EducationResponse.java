package com.zosh.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationResponse {
  private Long id;
  private String institutionName;
  private String degree;
  private String fieldOfStudy;
  private String grade;
  private String startDate;
  private String endDate;
  private Boolean isCurrentEducation = false;
  private String description;
  private Integer displayOrder;
}
