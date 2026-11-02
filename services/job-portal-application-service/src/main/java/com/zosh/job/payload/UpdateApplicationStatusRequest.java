package com.zosh.job.payload;

import com.zosh.job.domain.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateApplicationStatusRequest {
  @NotNull(message = "Status is required")
  private ApplicationStatus status;
}
