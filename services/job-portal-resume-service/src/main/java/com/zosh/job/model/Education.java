package com.zosh.job.model;

import com.zosh.job.db.BaseTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "educations")
public class Education extends BaseTable {
  @ManyToOne
  @JoinColumn(nullable = false)
  private Resume resume;

  @Column(nullable = false, length = 200)
  private String institutionName;

  @Column(nullable = false, length = 150)
  private String degree;

  @Column(length = 150)
  private String fieldOfStudy;

  @Column(length = 50)
  private String grade;

  @Column(nullable = false)
  private String startDate;

  private String endDate;

  @Column(nullable = false)
  @Builder.Default
  private Boolean isCurrentEducation = false;

  private String description;

  @Builder.Default private Integer displayOrder = 0;
}
