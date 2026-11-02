package com.zosh.job.service;

import com.zosh.job.domain.ApplicationStatus;
import com.zosh.job.dto.ApplicationResponse;
import com.zosh.job.model.Application;
import com.zosh.job.payload.CompanyApplicationFilterRequest;
import com.zosh.job.payload.CreateApplicationRequest;
import com.zosh.job.payload.WithdrawApplicationRequest;
import java.util.List;

public interface ApplicationService {
  ApplicationResponse createApplication(Long candidateId, CreateApplicationRequest req)
      throws Exception;

  ApplicationResponse getApplicationById(Long id) throws Exception;

  List<ApplicationResponse> getMyApplications(Long candidateId) throws Exception;

  List<ApplicationResponse> getApplicationsForJob(
      Long jobId, CompanyApplicationFilterRequest filter) throws Exception;

  List<ApplicationResponse> getApplicatonsForCompany(
      Long userId, CompanyApplicationFilterRequest filter) throws Exception;

  ApplicationResponse updateStatus(Long applicationId, Long employerId, ApplicationStatus status)
      throws Exception;

  ApplicationResponse withdraw(
      Long applicationId, Long candidateId, WithdrawApplicationRequest withrwo) throws Exception;

  ApplicationResponse toggleStar(Long applicationId, Long employerId) throws Exception;

  void deleteApplication(Long applicationId, Long candidateId) throws Exception;

  Application getApplicationEntity(Long id) throws Exception;
}
