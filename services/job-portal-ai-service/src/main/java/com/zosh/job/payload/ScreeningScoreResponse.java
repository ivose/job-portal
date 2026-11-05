package com.zosh.job.payload;

import java.util.List;
import lombok.Data;

@Data
public class ScreeningScoreResponse {
  private int score;
  private int skillMatchScore;
  private int experienceMatchScore;
  private int educationMatchScore;
  private List<String> matchedSkills;
  private List<String> missingSkills;
  private List<String> strengths;
  private List<String> concerns;
  private String summary;
}
