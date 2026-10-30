package com.zosh.job.dto;

import com.zosh.job.domain.ResumeTemplate;
import com.zosh.job.domain.ResumeVisibility;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumeResponse {
  private Long id;
  private Long candidateId;
  private String title;
  private ResumeTemplate template;
  private ResumeVisibility visibility;
  private Boolean isDefault;
  private PersonalInfoResponse personalInfo;
  private String summary;
  private Integer completionScore;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
