package com.zosh.job.repository;

import com.zosh.job.model.JobSkill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobSkillsRepository
    extends JpaRepository<JobSkill, Long>, JpaSpecificationExecutor<JobSkill> {

  List<JobSkill> findByActiveTrue();

  boolean existsByName(String name);

  boolean existsBySlug(String slug);
}
