package com.zosh.job.mapper;

import com.zosh.job.dto.CompanyResponse;
import com.zosh.job.dto.JobResponse;
import com.zosh.job.dto.JobSkillResponse;
import com.zosh.job.dto.JobTagResponse;
import com.zosh.job.model.Job;
import com.zosh.job.model.embeddable.JobLocation;
import com.zosh.job.model.embeddable.SalaryRange;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class JobMapper {

  public static JobResponse toResponse(Job job, CompanyResponse companyRes) {
    if (job == null) {
      return null;
    }

    JobLocation location = job.getLocation();
    SalaryRange salary = job.getSalaryRange();

    Set<JobSkillResponse> skills =
        job.getSkills() == null
            ? Collections.emptySet()
            : job.getSkills().stream()
                .map(JobSkillMapper::toJobSkillResponse)
                .collect(Collectors.toSet());

    Set<JobTagResponse> tags =
        job.getTags() == null
            ? Collections.emptySet()
            : job.getTags().stream()
                .map(JobTagMapper::toJobTagResponse)
                .collect(Collectors.toSet());

    return JobResponse.builder()
        .id(job.getId())
        .title(job.getTitle())
        .description(job.getDescription())
        .requirements(job.getRequirements())
        .responsibilities(job.getResponsibilities())
        .benefits(job.getBenefits())
        .company(companyRes)
        .tags(tags)
        .skills(skills)
        .category(JobCategoryMapper.toResponse(job.getCatecory(), false))
        .employerId(job.getEmployerId())
        .address(location == null ? null : location.getAddress())
        .city(location == null ? null : location.getCity())
        .state(location == null ? null : location.getState())
        .country(location == null ? null : location.getCountry())
        .zipCode(location == null ? null : location.getZipCode())
        .minSalary(salary == null ? null : salary.getMinSalary())
        .maxSalary(salary == null ? null : salary.getMaxSalary())
        .jobType(job.getJobType())
        .workMode(job.getWorkMode())
        .experienceLevel(job.getExperienceLevel())
        .jobStatus(job.getJobStatus())
        .openings(job.getOpenings())
        .applicationDeadLine(job.getApplicationDeadLine())
        .expiresAt(job.getExpiredAt())
        .active(job.getActive())
        .applicationCount(job.getApplicationCount())
        .createdAt(job.getCreatedAt())
        .updatedAt(job.getUpdatedAt())
        .publishedAt(job.getPublishedAt())
        .closedAt(job.getClosedAt())
        .build();
  }
}
