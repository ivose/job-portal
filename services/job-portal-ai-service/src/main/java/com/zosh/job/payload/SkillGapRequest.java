package com.zosh.job.payload;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Data;

@Data
public class SkillGapRequest {

  @NotBlank(message = "Job title is required")
  private String jobTitle;

  private List<String> candidateSkills;

  private List<String> requiredSkills;
}
