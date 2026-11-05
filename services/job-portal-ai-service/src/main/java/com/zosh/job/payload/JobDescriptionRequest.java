package com.zosh.job.payload;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Data;

@Data
public class JobDescriptionRequest {

  @NotBlank(message = "Title is required")
  private String title;

  private List<String> skills;

  private String experienceLevel;

  private String jobType;

  private String workMode;

  private String category;

  private String additionalContext;
}
