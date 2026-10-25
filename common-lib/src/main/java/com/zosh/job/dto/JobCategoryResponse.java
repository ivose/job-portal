package com.zosh.job.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCategoryResponse {
  private Long id;
  private String name;
  private String slug;
  private String description;
  private String iconUrl;
  private Boolean active;

  private Long parentId;
  private String parentName;

  private List<JobCategoryResponse> subCategories;
  private LocalDateTime createdAt;
}
