package com.zosh.job.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchEncanceRequest {

  @NotBlank(message = "Query is required")
  private String query;
}
