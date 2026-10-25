package com.zosh.job.service;

import com.zosh.job.dto.JobSkillResponse;
import com.zosh.job.model.JobSkill;
import com.zosh.job.payload.JobSkillRequest;
import java.util.List;
import java.util.Set;

public interface JobSkillService {
  JobSkillResponse createJobSkill(JobSkillRequest req) throws Exception;

  List<JobSkillResponse> getAllJobSkills() throws Exception;

  JobSkillResponse getSkillById(Long id) throws Exception;

  JobSkillResponse updateSkill(Long id, JobSkillRequest req) throws Exception;

  void deleteSkill(Long id) throws Exception;

  Set<JobSkill> getSkillsByIds(Set<Long> ids) throws Exception;
}
