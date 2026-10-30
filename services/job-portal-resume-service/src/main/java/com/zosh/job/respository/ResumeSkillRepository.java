package com.zosh.job.respository;

import com.zosh.job.model.ResumeSkill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeSkillRepository extends JpaRepository<ResumeSkill, Long> {
  List<ResumeSkill> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);
}
