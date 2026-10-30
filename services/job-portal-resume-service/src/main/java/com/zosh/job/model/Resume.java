package com.zosh.job.model;

import com.zosh.job.db.BaseTable;
import com.zosh.job.domain.ResumeTemplate;
import com.zosh.job.domain.ResumeVisibility;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "resumes")
public class Resume extends BaseTable {

  @Column(nullable = false)
  private Long candidateId;

  @Column(nullable = false)
  private String title;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ResumeTemplate template = ResumeTemplate.PROFESSIONAL;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ResumeVisibility visibility = ResumeVisibility.PUBLIC;

  @Builder.Default
  @Column(nullable = false)
  private Boolean isDefault = false;

  @Embedded private PersonalInfo personalInfo;

  private String summary;

  @Builder.Default private Integer completionScore = 0;

  @Builder.Default
  @Column(nullable = false)
  private Boolean isActive = true;
}
