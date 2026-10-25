package com.zosh.job.mapper;

import com.zosh.job.dto.JobSkillResponse;
import com.zosh.job.model.JobSkill;

public class JobSkillMapper {

  public static JobSkillResponse toJobSkillResponse(JobSkill jobSkill) {
    return JobSkillResponse.builder()
        .id(jobSkill.getId())
        .name(jobSkill.getName())
        .slug(jobSkill.getSlug())
        .category(jobSkill.getCategory())
        .activate(jobSkill.getActive())
        .build();
  }
}
