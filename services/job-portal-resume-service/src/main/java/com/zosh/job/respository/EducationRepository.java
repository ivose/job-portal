package com.zosh.job.respository;

import com.zosh.job.model.Education;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
  List<Education> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);
}
