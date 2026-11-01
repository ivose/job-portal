package com.zosh.job.payload;

import com.zosh.job.domain.LangugageProficiency;
import jakarta.validation.constraints.NotBlank;
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
public class AddLanguageRequest {

  @NotBlank(message = "Language name is required")
  private String languageName;

  @NotNull(message = "Proficiency is required")
  private LangugageProficiency proficiency;

  private Integer displayOrder;
}
