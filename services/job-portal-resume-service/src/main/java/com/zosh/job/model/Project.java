package com.zosh.job.model;

import com.zosh.job.db.BaseTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "projects")
public class Project extends BaseTable {
  @ManyToOne
  @JoinColumn(nullable = false)
  private Resume resume;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Builder.Default
  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> technologies = new ArrayList<>();

  private String projectUrl;

  private String sourceCodeUrl;

  @Column(nullable = false)
  private String startDate;

  private String endDate;

  @Column(nullable = false)
  @Builder.Default
  private Boolean isonGoeing = false;

  @Builder.Default private Integer displayOrder = 0;
}
