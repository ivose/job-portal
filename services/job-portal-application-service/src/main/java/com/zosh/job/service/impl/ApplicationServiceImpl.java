package com.zosh.job.service.impl;

import com.zosh.job.client.CompanyClient;
import com.zosh.job.client.JobClient;
import com.zosh.job.domain.ApplicationStatus;
import com.zosh.job.dto.ApplicationResponse;
import com.zosh.job.dto.CompanyResponse;
import com.zosh.job.dto.JobResponse;
import com.zosh.job.dto.response.UserResponse;
import com.zosh.job.mapper.ApplicationMapper;
import com.zosh.job.model.Application;
import com.zosh.job.model.ApplicationNote;
import com.zosh.job.payload.CompanyApplicationFilterRequest;
import com.zosh.job.payload.CreateApplicationRequest;
import com.zosh.job.payload.WithdrawApplicationRequest;
import com.zosh.job.repository.ApplicationNoteRepository;
import com.zosh.job.repository.ApplicationRepository;
import com.zosh.job.repository.ApplicationSpecification;
import com.zosh.job.service.ApplicationService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final JobClient jobClient;
  private final CompanyClient companyClient;
  private final ApplicationNoteRepository applicationNoteRepository;

  @Override
  public ApplicationResponse createApplication(Long candidateId, CreateApplicationRequest req)
      throws Exception {
    if (applicationRepository.existsByCandidateIdAndJobId(candidateId, req.getJobId())) {
      throw new Exception("Application already exists");
    }
    Long companyId = getCompanyId(req.getJobId());
    Long employerId = getEmployerId(companyId);
    Application application = ApplicationMapper.toEntity(req, candidateId, companyId, employerId);
    Application saved = applicationRepository.save(application);
    // todo: ai screening runs in a background thread, no callback needde
    return buildFullResponse(saved);
  }

  @Override
  public ApplicationResponse getApplicationById(Long id) throws Exception {
    Application application = getApplicationEntity(id);
    return buildFullResponse(application);
  }

  @Override
  public List<ApplicationResponse> getMyApplications(Long candidateId) throws Exception {
    return applicationRepository.findByCandidateId(candidateId).stream()
        .map(this::buildFullResponse)
        .toList();
  }

  @Override
  public List<ApplicationResponse> getApplicationsForJob(
      Long jobId, CompanyApplicationFilterRequest filter) throws Exception {
    Sort sort = buildSort(filter.getSortBy());
    return applicationRepository
        .findAll(
            ApplicationSpecification.forJobWithFilters(
                jobId,
                filter.getStatus(),
                filter.getIsStarred(),
                filter.getAiShortListStatus(),
                filter.getMinAiScore()),
            sort)
        .stream()
        .map(this::buildFullResponse)
        .toList();
  }

  @Override
  public List<ApplicationResponse> getApplicatonsForCompany(
      Long userId, CompanyApplicationFilterRequest filter) throws Exception {
    Long companyId = getMyCompanyId(userId);
    Sort sort = buildSort(filter.getSortBy());
    return applicationRepository
        .findAll(
            ApplicationSpecification.forCompanyWithFilters(
                companyId,
                filter.getJob(),
                filter.getStatus(),
                filter.getIsStarred(),
                filter.getAiShortListStatus(),
                filter.getMinAiScore()),
            sort)
        .stream()
        .map(this::buildFullResponse)
        .toList();
  }

  @Override
  public ApplicationResponse updateStatus(
      Long applicationId, Long employerId, ApplicationStatus status) throws Exception {
    Application application = getApplicationEntity(applicationId);
    assertEmployer(application, employerId);
    if (application.getStatus() == ApplicationStatus.WITHDRAWN) {
      throw new Exception("Candidate have already withdrawn");
    }
    application.setStatus(status);
    Application savedApplication = applicationRepository.save(application);
    return buildFullResponse(savedApplication);
  }

  @Override
  public ApplicationResponse withdraw(
      Long applicationId, Long candidateId, WithdrawApplicationRequest withrwo) throws Exception {
    Application application = getApplicationEntity(applicationId);
    assertCandidate(application, candidateId);
    application.setStatus(ApplicationStatus.WITHDRAWN);
    application.setWithrawnReason(withrwo.getReason());
    Application savedApplication = applicationRepository.save(application);
    return buildFullResponse(savedApplication);
  }

  @Override
  public ApplicationResponse toggleStar(Long applicationId, Long employerId) throws Exception {
    Application application = getApplicationEntity(applicationId);
    assertEmployer(application, employerId);
    application.setIsStarred(!application.getIsStarred());
    Application savedApplication = applicationRepository.save(application);
    return buildFullResponse(savedApplication);
  }

  @Override
  public void deleteApplication(Long applicationId, Long candidateId) throws Exception {
    Application application = getApplicationEntity(applicationId);
    assertCandidate(application, candidateId);
    applicationRepository.delete(application);
  }

  @Override
  public Application getApplicationEntity(Long id) throws Exception {
    return applicationRepository
        .findById(id)
        .orElseThrow(() -> new Exception("Application not found"));
  }

  public ApplicationResponse buildFullResponse(Application application) {
    JobResponse job = JobResponse.builder().id(application.getJobId()).build();
    CompanyResponse company = CompanyResponse.builder().id(application.getCompanyId()).build();
    UserResponse candidate = UserResponse.builder().id(application.getCandidateId()).build();
    List<ApplicationNote> notes =
        applicationNoteRepository.findByApplicationId(application.getId());
    return ApplicationMapper.toResponse(application, notes, job, company, candidate);
  }

  private Sort buildSort(String sortBy) {
    if ("AI_SCORE_DESC".equals(sortBy)) {
      return Sort.by(Sort.Order.desc("aiScore").with(Sort.NullHandling.NULLS_LAST));
    }
    if ("AI_SCORE_ASC".equals(sortBy)) {
      return Sort.by(Sort.Order.asc("aiScore").with(Sort.NullHandling.NULLS_LAST));
    }
    return Sort.by(Sort.Direction.DESC, "appliedAt");
  }

  private void assertEmployer(Application application, Long employerId) throws Exception {
    if (!application.getEmployerId().equals(employerId)) {
      throw new Exception("You are not the employer for this application");
    }
  }

  private void assertCandidate(Application application, Long candidateId) throws Exception {
    if (!application.getCandidateId().equals(candidateId)) {
      throw new Exception("You are not the owner for this application");
    }
  }

  private Long getCompanyId(Long jobId) throws Exception {
    JobClient.JobInfo job = jobClient.getJobById(jobId);
    if (job == null || job.company() == null || job.company().id() == null)
      throw new Exception("Job or company not found");
    return job.company().id();
  }

  private Long getEmployerId(Long companyId) throws Exception {
    CompanyClient.CompanyInfo company = companyClient.getCompanyById(companyId);
    if (company == null || company.ownerId() == null) {
      throw new Exception("Company or employer not found");
    }
    return company.ownerId();
  }

  private Long getMyCompanyId(Long userId) throws Exception {
    CompanyClient.CompanyInfo company = companyClient.getMyCompany(userId);
    if (company == null || company.id() == null) {
      throw new Exception("Company not found for employer " + userId);
    }
    return company.id();
  }
}
