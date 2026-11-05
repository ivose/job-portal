package com.zosh.job.payload;

import java.util.List;
import lombok.Data;

@Data
public class ScreeningScoreRequest {
  private String jobTitle;
  private String experienceLevel;
  private List<String> requiredSkills;
  private String responsibilities;
  private String candidateSummary;
  private List<String> candidateSkills;
  private List<String> candidateExperience;
}
