package com.zosh.job.service;

import com.zosh.job.payload.AiTextResponse;
import com.zosh.job.payload.CareerFeedbackRequest;
import com.zosh.job.payload.CareerFeedbackResponse;
import com.zosh.job.payload.ResumeImprovementRequest;
import com.zosh.job.payload.ResumeImprovementResponse;
import com.zosh.job.payload.ResumeSummaryRequest;
import com.zosh.job.payload.WorkExperienceBulletRequest;
import com.zosh.job.payload.WorkExperienceBulletsResponse;

public interface ResumeAiService {
  public AiTextResponse generateProfessionalSummary(ResumeSummaryRequest req) throws Exception;

  public WorkExperienceBulletsResponse generateWorkExperienceBullets(
      WorkExperienceBulletRequest req) throws Exception;

  public CareerFeedbackResponse generateCareerFeedback(CareerFeedbackRequest req) throws Exception;

  public ResumeImprovementResponse getResumeImprovementTips(ResumeImprovementRequest req)
      throws Exception;
}
