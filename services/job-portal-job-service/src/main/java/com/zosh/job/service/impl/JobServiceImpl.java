package com.zosh.job.service.impl;

import com.zosh.job.client.CompanyClient;
import com.zosh.job.domain.JobStatus;
import com.zosh.job.dto.CompanyResponse;
import com.zosh.job.dto.JobRequest;
import com.zosh.job.dto.JobResponse;
import com.zosh.job.mapper.JobMapper;
import com.zosh.job.model.Job;
import com.zosh.job.model.JobCategory;
import com.zosh.job.model.JobSkill;
import com.zosh.job.model.JobTag;
import com.zosh.job.model.embeddable.JobLocation;
import com.zosh.job.model.embeddable.SalaryRange;
import com.zosh.job.payload.JobSearchRequest;
import com.zosh.job.repository.JobRepository;
import com.zosh.job.repository.JobSpecification;
import com.zosh.job.service.JobCategoryService;
import com.zosh.job.service.JobService;
import com.zosh.job.service.JobSkillService;
import com.zosh.job.service.JobTagService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements JobService {

  private final JobRepository jobRepository;
  private final JobCategoryService jobCategoryService;
  private final JobSkillService jobSkillService;
  private final JobTagService jobTagService;
  private final CompanyClient companyClient;

  @Override
  @Transactional
  public JobResponse createJob(Long employerId, JobRequest req) throws Exception {

    Long companyId = companyClient.getCompanyIdByEmployerId(employerId);

    Job job =
        Job.builder()
            .companyId(companyId)
            .employerId(employerId)
            .jobStatus(JobStatus.DRAFT)
            .active(true)
            .applicationCount(0)
            .build();

    applyRequest(job, req);

    Job saved = jobRepository.save(job);

    return toResponse(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public JobResponse getJobById(Long jobId) throws Exception {

    return toResponse(findJob(jobId));
  }

  @Override
  @Transactional(readOnly = true)
  public List<JobResponse> getJobs(JobSearchRequest request) throws Exception {

    if (request == null) {
      request = new JobSearchRequest();
    }

    return jobRepository.findAll(JobSpecification.build(request)).stream()
        .map(this::toResponse)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<JobResponse> getJobsByCompanyId(Long companyId) throws Exception {

    return jobRepository.findByCompanyId(companyId).stream().map(this::toResponse).toList();
  }

  @Override
  @Transactional
  public JobResponse updateJob(Long jobId, Long employerId, JobRequest req) throws Exception {

    Job job = findJob(jobId);
    assertEmployer(job, employerId);
    if (job.getJobStatus() == JobStatus.CLOSED) {
      throw new IllegalStateException("Cannot update a closed job");
    }

    applyRequest(job, req);

    return toResponse(jobRepository.save(job));
  }

  @Override
  @Transactional
  public JobResponse publishJob(Long jobId, Long employerId) throws Exception {

    Job job = findJob(jobId);
    assertEmployer(job, employerId);

    if (job.getJobStatus() != JobStatus.DRAFT) {
      throw new IllegalStateException("Only draft jobs can be published");
    }

    if (job.getApplicationDeadLine() != null
        && job.getApplicationDeadLine().isBefore(LocalDate.now())) {
      throw new IllegalStateException("Application deadline has already passed");
    }

    job.setJobStatus(JobStatus.OPEN);
    job.setActive(true);
    job.setPublishedAt(LocalDateTime.now());
    job.setClosedAt(null);

    return toResponse(jobRepository.save(job));
  }

  @Override
  @Transactional
  public JobResponse closeJob(Long jobId, Long employerId) throws Exception {

    Job job = findJob(jobId);
    assertEmployer(job, employerId);

    if (job.getJobStatus() != JobStatus.OPEN) {
      throw new IllegalStateException("Only open jobs can be closed");
    }

    job.setJobStatus(JobStatus.CLOSED);
    job.setActive(false);
    job.setClosedAt(LocalDateTime.now());

    return toResponse(jobRepository.save(job));
  }

  @Override
  @Transactional
  public JobResponse deleteJob(Long jobId, Long employerId) throws Exception {

    Job job = findJob(jobId);
    assertEmployer(job, employerId);

    JobResponse response = toResponse(job);
    jobRepository.delete(job);

    return response;
  }

  @Override
  @Transactional
  public void incrementApplicationCount(Long jobId) throws Exception {

    Job job = findJob(jobId);

    if (!Boolean.TRUE.equals(job.getActive()) || job.getJobStatus() != JobStatus.OPEN) {
      throw new IllegalStateException("Applications are not accepted for this job");
    }

    if (job.getApplicationDeadLine() != null
        && job.getApplicationDeadLine().isBefore(LocalDate.now())) {
      throw new IllegalStateException("Application deadline has passed");
    }

    int current = job.getApplicationCount() == null ? 0 : job.getApplicationCount();

    job.setApplicationCount(current + 1);
    jobRepository.save(job);
  }

  @Override
  @Transactional(readOnly = true)
  public List<JobResponse> getAllJobsAdmin() throws Exception {

    return jobRepository.findAll().stream().map(this::toResponse).toList();
  }

  private Job findJob(Long jobId) throws Exception {

    return jobRepository
        .findById(jobId)
        .orElseThrow(() -> new Exception("Job not found with ID: " + jobId));
  }

  private void assertEmployer(Job job, Long employerId) throws Exception {

    if (!Objects.equals(job.getEmployerId(), employerId)) {
      throw new SecurityException("You are not the owner of this job");
    }
  }

  private void applyRequest(Job job, JobRequest req) throws Exception {

    JobCategory category = jobCategoryService.getCategoryEntityById(req.getCategoryId());

    Set<JobSkill> skills =
        req.getSkillIds() == null
            ? Collections.emptySet()
            : jobSkillService.getSkillsByIds(req.getSkillIds());

    Set<JobTag> tags =
        req.getTagIds() == null
            ? Collections.emptySet()
            : jobTagService.getTagsByIds(req.getTagIds());

    SalaryRange salary = buildSalaryRange(req);

    if (salary.getMinSalary() != null
        && salary.getMaxSalary() != null
        && salary.getMinSalary().compareTo(salary.getMaxSalary()) > 0) {
      throw new IllegalArgumentException("Minimum salary cannot exceed maximum salary");
    }

    job.setTitle(req.getTitle());
    job.setDescription(req.getDescription());
    job.setRequirements(req.getRequirements());
    job.setResponsibilities(req.getResponsibilities());
    job.setBenefits(req.getBenefits());

    job.setCatecory(category);
    job.setSkills(skills);
    job.setTags(tags);

    job.setLocation(buildLocation(req));
    job.setSalaryRange(salary);

    job.setJobType(req.getJobType());
    job.setWorkMode(req.getWorkMode());
    job.setExperienceLevel(req.getExperienceLevel());

    job.setOpenings(req.getOpenings() == null ? 1 : req.getOpenings());

    job.setApplicationDeadLine(req.getApplicationDeadLine());

    job.setExpiredAt(req.getExpiredAt());
  }

  private SalaryRange buildSalaryRange(JobRequest req) {

    return SalaryRange.builder()
        .minSalary(req.getMinSalary())
        .maxSalary(req.getMaxSalary())
        .build();
  }

  private JobLocation buildLocation(JobRequest req) {

    return JobLocation.builder()
        .address(req.getAddress())
        .city(req.getCity())
        .state(req.getState())
        .country(req.getCountry())
        .zipCode(req.getZipCode())
        .build();
  }

  private JobResponse toResponse(Job job) {
    CompanyResponse company = companyClient.getCompanyById(job.getCompanyId());

    return JobMapper.toResponse(job, company);
  }
}
