package com.zosh.job.model;

import com.zosh.job.db.BaseTable;
import com.zosh.job.domain.ProficiencyLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "resume_skills")
public class ResumeSkill extends BaseTable {

  @ManyToOne
  @JoinColumn(nullable = false)
  private Resume resume;

  @Column(nullable = false)
  private String skillName;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private ProficiencyLevel proficiencyLevel = ProficiencyLevel.BEGINNER;

  private Integer yearsOfExperience;

  @Builder.Default
  @Column(nullable = false)
  private Integer displayOrder = 0;
}
