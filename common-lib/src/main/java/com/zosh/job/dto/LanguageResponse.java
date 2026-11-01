package com.zosh.job.dto;

import com.zosh.job.domain.LanguageProficiency;
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
public class LanguageResponse {
  private Long id;
  private String languageName;
  private LanguageProficiency proficiency;
  private Integer displayOrder;
}
