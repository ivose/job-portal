package com.zosh.job.model;

import com.zosh.job.db.BaseTable;
import com.zosh.job.domain.LanguageProficiency;
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
@Table(name = "languages")
public class Language extends BaseTable {

  @ManyToOne
  @JoinColumn(nullable = false)
  private Resume resume;

  @Column(nullable = false)
  private String languageName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private LanguageProficiency proficiency;

  @Column(nullable = false)
  @Builder.Default
  private Integer displayOrder = 0;
}
