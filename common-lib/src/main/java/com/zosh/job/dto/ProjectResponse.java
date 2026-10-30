package com.zosh.job.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {
  private Long id;
  private String title;
  private String description;
  private List<String> technologies;
  private String projectUrl;
  private String sourceCodeUrl;
  private String startDate;
  private String endDate;
  private Boolean isonGoeing;
  private Integer displayOrder;
}
