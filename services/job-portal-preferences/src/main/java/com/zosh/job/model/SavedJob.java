package com.zosh.job.model;

import com.zosh.job.db.BaseTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "saved_jobs")
public class SavedJob extends BaseTable {

  @Column(nullable = false)
  private Long candidateId;

  @Column(nullable = false)
  private Long jobId;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime savedAt;
}
