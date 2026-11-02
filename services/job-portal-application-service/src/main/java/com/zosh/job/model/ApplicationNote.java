package com.zosh.job.model;

import com.zosh.job.db.BaseTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
@Table(name = "application_notes")
public class ApplicationNote extends BaseTable {
  @ManyToOne
  @JoinColumn(nullable = false)
  private Application application;

  @Column(nullable = false)
  private Long addedByUserId;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;
}
