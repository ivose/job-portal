package com.zosh.job.payload;

import java.util.List;
import lombok.Data;

@Data
public class CareerFeedbackResponse {
  private int profileStrength;
  private List<String> shortlistingIssues;
  private List<Improvement> improvements;
  private List<JobTarget> targetJobs;
  private String overallSummary;

  @Data
  public static class Improvement {
    private String area; // e.g "Skills", "Summary", "Experience"
    private String issue; // what is weak or missing
    private String action; // concrete step to fix it
    private String priority;
  }

  @Data
  public static class JobTarget {
    private String jobTitle; // e.g "Junior frontend developer"
    private String reason; // why this role fits the pofile
    private String skillMatch;
  }
}
