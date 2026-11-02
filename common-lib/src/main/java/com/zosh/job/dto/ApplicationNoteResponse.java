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
public class ApplicationNoteResponse {
  private Long id;
  private Long addedByUserId;
  private String content;
  private LocalDateTime createdAt;
}
