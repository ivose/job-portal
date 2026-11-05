package com.zosh.job.payload;

import java.util.List;
import lombok.Data;

@Data
public class SearchEncanceResponse {
  private List<String> keywords;
  private List<String> locations;
  private List<String> jobTypes;
  private List<String> workModes;
  private List<String> experienceLevels;
  private Long minSalary;
  private List<String> skills;
}
