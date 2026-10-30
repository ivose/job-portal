package com.zosh.job.model;

import com.zosh.job.db.BaseTable;
import com.zosh.job.domain.JobType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "work_experiences")
public class WorkExperience extends BaseTable {
  @ManyToOne
  @JoinColumn(nullable = false)
  private Resume resume;

  @Column(nullable = false)
  private String companyName;

  private String companyLogoUrl;

  @Column(nullable = false)
  private String jobTitle;

  @Enumerated(EnumType.STRING)
  private JobType employmentType;

  private String location;

  @Column(nullable = false)
  private LocalDate startDate;

  private LocalDate endDate;

  @Builder.Default
  @Column(nullable = false)
  private Boolean isCurrentJob = false;

  private String description;

  @Builder.Default @ElementCollection private List<String> technologies = new ArrayList<>();

  @Builder.Default
  @Column(nullable = false)
  private Integer displayOrder = 0;
}
