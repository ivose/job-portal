package com.zosh.job.mapper;

import com.zosh.job.dto.ApplicationResponse;
import com.zosh.job.dto.CompanyResponse;
import com.zosh.job.dto.JobResponse;
import com.zosh.job.dto.response.UserResponse;
import com.zosh.job.model.Application;
import com.zosh.job.payload.CreateApplicationRequest;
import java.time.LocalDateTime;

public class ApplicationMapper {
  public static Application toEntity(
      CreateApplicationRequest req, Long candidateId, Long companyId, Long emplyerId) {
    return req == null
        ? null
        : Application.builder()
            .candidateId(candidateId)
            .jobId(req.getJobId())
            .companyId(companyId)
            .employerId(emplyerId)
            .resumeId(req.getResumeId())
            .coverLetter(req.getCoverLetter())
            .expectedSalary(req.getExpectedSalary())
            .availableFrom(req.getAvailableFrom())
            .appliedAt(LocalDateTime.now())
            .build();
  }

  public static ApplicationResponse toResponse(
      Application application, JobResponse job, CompanyResponse company, UserResponse candidate) {
    return application == null
        ? null
        : ApplicationResponse.builder()
            .id(application.getId())
            .candidateId(application.getCandidateId())
            .aiScore(application.getAiScore())
            .aishortListStatus(application.getAishortListStatus())
            .candidate(candidate)
            .employerId(application.getEmployerId())
            .job(job)
            .company(company)
            .status(application.getStatus())
            .resumeId(application.getResumeId())
            .coverLetter(application.getCoverLetter())
            .expectedSalary(application.getExpectedSalary())
            .availableFrom(application.getAvailableFrom())
            .isRead(application.getIsRead())
            .isStarred(application.getIsStarred())
            .withdrawnAt(application.getWithdrawnAt())
            .withrawnReason(application.getWithrawnReason())
            .appliedAt(application.getAppliedAt())
            .updatedAt(application.getUpdatedAt())
            .build();
  }
}
