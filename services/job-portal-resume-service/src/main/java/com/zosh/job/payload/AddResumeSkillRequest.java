package com.zosh.job.payload;

import com.zosh.job.domain.ProficiencyLevel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddResumeSkillRequest {
  @NotBlank(message = "Skill name is mandatory")
  @Size(min = 1, max = 100, message = "Skill name should be between 1 and 100 characters")
  private String skillName;

  @NotNull(message = "Proficiency level is mandatory")
  private ProficiencyLevel proficiencyLevel;

  @Min(value = 0, message = "Years of experience should be greater than or equal to 0")
  private Integer yearsOfExperience;

  private Integer displayOrder;
}
