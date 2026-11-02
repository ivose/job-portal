package com.zosh.job.payload;

import com.zosh.job.domain.AishortListStatus;
import com.zosh.job.domain.ApplicationStatus;
import lombok.Data;

@Data
public class CompanyApplicationFilterRequest {
  private Long job;
  private ApplicationStatus status;
  private Boolean isStarred;
  private AishortListStatus aiShortListStatus;
  private Integer minAiScore;
  private String sortBy;
}
