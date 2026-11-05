package com.zosh.job.service;

import com.zosh.job.payload.AiTextResponse;
import com.zosh.job.payload.JobDescriptionRequest;
import com.zosh.job.payload.SalaryRangeRequest;
import com.zosh.job.payload.SalaryRangeResponse;

public interface JobAiService {
  public AiTextResponse generateJobDescription(JobDescriptionRequest req) throws Exception;

  public AiTextResponse generateJobRequirements(String title, String category) throws Exception;

  public SalaryRangeResponse generateSalaryRange(SalaryRangeRequest req) throws Exception;

  public AiTextResponse generateJobResponsibilities(String title, String category) throws Exception;

  public AiTextResponse generateJobBenefits(String title, String category, String jobType)
      throws Exception;

  public AiTextResponse recommendedSkillsForJob(String jobTitle, String description)
      throws Exception;

  public AiTextResponse recommendTagForJob(String jobTitle, String description) throws Exception;
}
