package com.zosh.job.dto;

import com.zosh.job.domain.SkillCategory;
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
public class JobSkillResponse {
  private Long id;
  private String name;
  private String slug;
  private SkillCategory category;
  private boolean activate;
}
