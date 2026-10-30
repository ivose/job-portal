package com.zosh.job.mapper;

import com.zosh.job.dto.WorkExperienceResponse;
import com.zosh.job.model.WorkExperience;
import java.util.ArrayList;
import java.util.List;

public class WorkExperienceMapper {
  public static WorkExperienceResponse toWorkExperienceResponse(WorkExperience exp) {
    return exp == null
        ? null
        : WorkExperienceResponse.builder()
            .id(exp.getId())
            .companyName(exp.getCompanyName())
            .companyLogoUrl(exp.getCompanyLogoUrl())
            .jobTitle(exp.getJobTitle())
            .employmentType(exp.getEmploymentType())
            .location(exp.getLocation())
            .startDate(exp.getStartDate())
            .endDate(exp.getEndDate())
            .isCurrentJob(exp.getIsCurrentJob())
            .description(exp.getDescription())
            .technologies(
                exp.getTechnologies() == null ? List.of() : new ArrayList<>(exp.getTechnologies()))
            .displayOrder(exp.getDisplayOrder())
            .build();
  }
}
