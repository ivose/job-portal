package com.zosh.job.payload;

import java.util.List;
import lombok.Data;

@Data
public class JobAlertSugestResponse {
  private List<String> suggestedKeywords;
  private List<String> suggestedLocations;
  private List<String> suggestedJobTypes;
  private List<String> suggestedWorkModes;
  private List<String> suggestedExperienceLevels;
  private List<String> suggestedIndustries;
  private String reasoning;
}
