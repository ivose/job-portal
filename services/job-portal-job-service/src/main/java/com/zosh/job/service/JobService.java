package com.zosh.job.service;

import com.zosh.job.dto.JobRequest;
import com.zosh.job.dto.JobResponse;
import com.zosh.job.payload.JobSearchRequest;
import java.util.List;

public interface JobService {
  JobResponse createJob(Long embloyerId, JobRequest req) throws Exception;

  JobResponse getJobById(Long jobId) throws Exception;

  List<JobResponse> getJobs(JobSearchRequest request) throws Exception;

  List<JobResponse> getJobsByCompanyId(Long companyId) throws Exception;

  JobResponse updateJob(Long jobId, Long embloyerId, JobRequest req) throws Exception;

  JobResponse publishJob(Long jobId, Long embloyerId) throws Exception;

  JobResponse closeJob(Long jobId, Long embloyerId) throws Exception;

  JobResponse deleteJob(Long jobId, Long embloyerId) throws Exception;

  void incrementApplicationCount(Long jobId) throws Exception;

  List<JobResponse> getAllJobsAdmin() throws Exception;
}
