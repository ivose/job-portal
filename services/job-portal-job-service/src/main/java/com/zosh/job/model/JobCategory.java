package com.zosh.job.model;

import com.zosh.job.db.BaseTable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "jobs_categories")
public class JobCategory extends BaseTable {

  @Column(nullable = false, unique = true)
  private String name;

  @Column(unique = true)
  private String slug;

  private String description;

  private String iconUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  private JobCategory parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<JobCategory> subCategories = new ArrayList<>();

  private Boolean active = true;
}
