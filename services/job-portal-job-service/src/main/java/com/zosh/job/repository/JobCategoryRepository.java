package com.zosh.job.repository;

import com.zosh.job.model.JobCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobCategoryRepository
    extends JpaRepository<JobCategory, Long>, JpaSpecificationExecutor<JobCategory> {
  boolean existsByName(String name);

  boolean existsBySlug(String slug);

  List<JobCategory> findByActiveTrue();
}
