package com.zosh.job.service;

import com.zosh.job.payload.AiTextResponse;
import com.zosh.job.payload.JobDescriptionRequest;
import com.zosh.job.payload.SalaryRangeRequest;
import com.zosh.job.payload.SalaryRangeResponse;

public interface JobAiService {
  AiTextResponse generateJobDescription(JobDescriptionRequest req) throws Exception;

  AiTextResponse generateJobRequirements(String title, String category) throws Exception;

  SalaryRangeResponse generateSalaryRange(SalaryRangeRequest req) throws Exception;

  AiTextResponse generateJobResponsibilities(String title, String category) throws Exception;

  AiTextResponse generateJobBenefits(String title, String category, String jobType)
      throws Exception;

  AiTextResponse recommendedSkillsForJob(String jobTitle, String description) throws Exception;

  AiTextResponse recommendTagForJob(String jobTitle, String description) throws Exception;
}
