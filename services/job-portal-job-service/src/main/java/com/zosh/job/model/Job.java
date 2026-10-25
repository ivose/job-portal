package com.zosh.job.model;

import com.zosh.job.db.BaseTable;
import com.zosh.job.domain.ExperienceLevel;
import com.zosh.job.domain.JobStatus;
import com.zosh.job.domain.JobType;
import com.zosh.job.domain.WorkMode;
import com.zosh.job.model.embeddable.JobLocation;
import com.zosh.job.model.embeddable.SalaryRange;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
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
@Table(name = "jobs")
public class Job extends BaseTable {

  @Column(nullable = false)
  private Long employerId;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private String requirements;

  @Column(nullable = false)
  private String responsibilities;

  @Column(nullable = false)
  private String benefits;

  @Column(nullable = false)
  private Long companyId;

  @ManyToOne private JobCategory catecory;

  @ManyToMany private Set<JobSkill> skills;

  @ManyToMany private Set<JobTag> tags;

  @Embedded private JobLocation location;

  @Embedded private SalaryRange salaryRange;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private JobType jobType;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private WorkMode workMode;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ExperienceLevel experienceLevel;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private JobStatus jobStatus = JobStatus.DRAFT;

  @Builder.Default private Integer openings = 1;

  @Builder.Default private Integer applicationCount = 0;

  private LocalDate applicationDeadLine;

  private LocalDate expiredAt;

  @Builder.Default private Boolean active = true;

  private LocalDateTime publishedAt;
  private LocalDateTime closedAt;
}
