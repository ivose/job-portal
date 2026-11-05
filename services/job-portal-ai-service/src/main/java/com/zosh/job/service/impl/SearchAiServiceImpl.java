package com.zosh.job.service.impl;

import com.zosh.job.client.GeminiClient;
import com.zosh.job.payload.JobAlertSugestResponse;
import com.zosh.job.payload.JobAlertSuggestRequest;
import com.zosh.job.payload.SearchEncanceRequest;
import com.zosh.job.payload.SearchEncanceResponse;
import com.zosh.job.service.SearchAiService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchAiServiceImpl implements SearchAiService {

  private final GeminiClient geminiClient;

  private static final String SYSTEM_PROMPT =
      """
            You are a job search expert and career advisor with deep knowledge of the Indian job market.
            You extract structured search criteria from natural language and provide data-driven career recommendations
            Always use the exact enum values specified in the prompt - never invent new values.
            When asked for JSON, respond ONLY with valid JSON - no explanation, no markdown fences.
            """;

  @Override
  public SearchEncanceResponse enhanceSearch(SearchEncanceRequest req) throws Exception {
    String prompt =
        """
                Extract structured job search criteria from this natural language query.

                User Query: %s

                Analyse the query and extract ALL implied and explicit search criteria.

                Valid jobTypes: FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP, FREELANCE
                Valid workModes: REMOTE, HYBRID, ON_SITE
                Valid experienceLevels: ENTRY, MID, SENIOR, LEAR, EXECUTIVE

                {
                  "keywords": ["keyword1", "keyword2"],
                  "locations": ["city1", "city2"],
                  "jobTypes": ["FULL_TIME"],
                  "workModes": ["REMOTE"],
                  "experienceLevels": ["ENTRY"],
                  "minSalary": null,
                  "skills": ["skill1", "skill2"]
                }

                Rules:
                - Only include fields that are mentioned or clearly implied
                - Use null for minSalary if not mentioned
                - Use empty arrays [] for fields not mentioned
                - "freshers" or "entry level" -> ENTRY experience level
                - "senior" or "5+ years" -> SENIOR experience level
                - "wfh" or "remote" -> REMOTE work mode
                """
            .formatted(req.getQuery());
    return geminiClient.generateJson(SYSTEM_PROMPT, prompt, SearchEncanceResponse.class);
  }

  @Override
  public JobAlertSugestResponse suggestJobAlertCriteria(JobAlertSuggestRequest req)
      throws Exception {
    String prompt =
        """
                Based on this candidate's profile, suggest optimal job alert criteria to find the best matching jobs.

                Candidate Profile:
                - Skills: %s
                - Experience level: %s
                - Previous Job Title: %s
                - Education: %s

                Valid jobTypes: FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP, FREELANCE
                Valid workModes: REMOTE, HYBRID, ON_SITE
                Valid experienceLevels: ENTRY, MID, SENIOR, LEAR, EXECUTIVE

                {
                  "suggestedKeywords": ["keyword1", "keyword2"],
                  "suggestedLocations": ["city1", "city2"],
                  "suggestedJobTypes": ["FULL_TIME"],
                  "suggestedWorkModes": ["REMOTE"],
                  "suggestedExperienceLevels": ["ENTRY"],
                  "suggestedIndustries": ["IT"],
                  "reasoning": "This candidate is a great fit for the job title."
                }
                """
            .formatted(
                getResult(req.getSkills()),
                Objects.toString(req.getExperienceLevel(), ""),
                getResult(req.getPreviousJobTitles()),
                getResult(req.getEducations()));
    return geminiClient.generateJson(SYSTEM_PROMPT, prompt, JobAlertSugestResponse.class);
  }

  private String getResult(List<String> input) {
    return input != null && !input.isEmpty() ? String.join(", ", input) : "Not provided";
  }
}
