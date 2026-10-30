package com.zosh.job.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddProjectRequest {
  @NotBlank(message = "Title is mandatory")
  private String title;

  private String description;
  private List<String> technologies = new ArrayList<>();

  @Pattern(regexp = "^(https?://).*", message = "Project URL must be a valid URL")
  private String projectUrl;

  @Pattern(regexp = "^(https?://).*", message = "Project code URL must be a valid URL")
  private String sourceCodeUrl;

  private String startDate;
  private String endDate;
  private Boolean isonGoeing = false;
  private Integer displayOrder;
}
