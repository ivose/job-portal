package com.zosh.job.respository;

import com.zosh.job.model.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  List<Project> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);
}
