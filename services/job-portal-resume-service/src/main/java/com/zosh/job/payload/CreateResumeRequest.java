package com.zosh.job.payload;

import com.zosh.job.domain.ResumeTemplate;
import com.zosh.job.domain.ResumeVisibility;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateResumeRequest {

  @NotBlank(message = "Title is required")
  private String title;

  private ResumeTemplate template;
  private ResumeVisibility visibility;
  private Boolean isDefault = false;
}
