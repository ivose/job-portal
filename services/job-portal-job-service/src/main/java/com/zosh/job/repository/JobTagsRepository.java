package com.zosh.job.repository;

import com.zosh.job.model.JobTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobTagsRepository
    extends JpaRepository<JobTag, Long>, JpaSpecificationExecutor<JobTag> {

  List<JobTag> findByActiveTrue();

  boolean existsByName(String name);

  boolean existsBySlug(String slug);
}
