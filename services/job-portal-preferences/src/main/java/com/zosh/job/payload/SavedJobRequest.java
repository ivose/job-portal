package com.zosh.job.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavedJobRequest {
  @NotNull(message = "Job ID is required")
  private Long jobId;

  private String notes;
}
