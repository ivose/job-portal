package com.zosh.job.payload;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Data;

@Data
public class CoverLetterRequest {

  @NotBlank(message = "Title is required")
  private String jobTitle;

  private String jobDescription;

  @NotBlank(message = "Candidate name is required")
  private String candidateName;

  private String candidateSummary;

  private List<String> candidateSkills;

  private List<String> candidateExperiences;

  private String targetCompanyName;
}
