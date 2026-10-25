package com.zosh.job.model;

import com.zosh.job.db.BaseTable;
import com.zosh.job.domain.SkillCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "job_skills")
public class JobSkill extends BaseTable {
  @Column(nullable = false, unique = true)
  private String name;

  @Column(unique = true)
  private String slug;

  private SkillCategory category;

  @Builder.Default
  @Column(nullable = false)
  private Boolean active = true;
}
