package com.zosh.job.payload;

import java.util.List;
import lombok.Data;

@Data
public class JobAlertSuggestRequest {
  private List<String> skills;
  private String experienceLevel;
  private List<String> previousJobTitles;
  private List<String> educations;
}
