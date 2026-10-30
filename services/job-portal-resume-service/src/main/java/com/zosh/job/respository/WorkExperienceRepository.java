package com.zosh.job.respository;

import com.zosh.job.model.WorkExperience;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
  List<WorkExperience> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);
}
