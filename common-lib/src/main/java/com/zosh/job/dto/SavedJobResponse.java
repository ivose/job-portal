package com.zosh.job.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedJobResponse {
  private Long id;
  private Long candidateId;
  private Long jobId;
  private LocalDateTime savedAt;
}
