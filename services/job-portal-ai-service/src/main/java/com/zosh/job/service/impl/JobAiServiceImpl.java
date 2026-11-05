package com.zosh.job.service.impl;

import com.zosh.job.client.GeminiClient;
import com.zosh.job.payload.AiTextResponse;
import com.zosh.job.payload.JobDescriptionRequest;
import com.zosh.job.payload.SalaryRangeRequest;
import com.zosh.job.payload.SalaryRangeResponse;
import com.zosh.job.service.JobAiService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobAiServiceImpl implements JobAiService {

  private final GeminiClient geminiClient;
  private static final String NOT_SPECIFIED = "Not specified";
  private static final String SYSTEM_PROMPT =
      """
            You are a senior HR professional and technical recruiter with deep knowledge of the Indian job market (2025-2026)
            You specialize in writing job description, compensation benchmarking, and talent acquisition.
            Always write in a professional, engaging, inclusive and bias-free tone.
            When asked for JSON, respond ONLY with valid JSON - no explanation, no markdown fences.
            """;

  @Override
  public AiTextResponse generateJobDescription(JobDescriptionRequest req) throws Exception {
    String skills = req.getSkills() != null ? String.join(", ", req.getSkills()) : NOT_SPECIFIED;
    String prompt =
        """
                Write a comprehensive, engaging, and inclusive job description.

                Job Details:
                - Title %s
                - Required Skills: %s
                - Experience Levels: %s
                - Job Type: %s
                - Work Mode: %s
                - Category: %s
                - Additional Context: %s

                Format the response in clean markdown with EXACTLY these sections:
                ## About the Role
                - [2-3 compelling sentences describing the role and its impact]

                ## Key Responsibilities
                - [6 specific, action-oriented bullet points]

                ## Requirements
                - [5-6 must-have qualifications and skills]

                ## Nice to Have
                - [3-4 bonus qualifications]

                ## What We Offer
                - [4-5 benefits and perks]

                Do NOT include placeholder company names.
                """
            .formatted(
                req.getTitle(),
                skills,
                Objects.toString(req.getExperienceLevel(), NOT_SPECIFIED),
                Objects.toString(req.getJobType(), NOT_SPECIFIED),
                Objects.toString(req.getWorkMode(), NOT_SPECIFIED),
                Objects.toString(req.getCategory(), NOT_SPECIFIED),
                Objects.toString(req.getAdditionalContext(), NOT_SPECIFIED));
    return AiTextResponse.builder()
        .content(geminiClient.generateText(SYSTEM_PROMPT, prompt))
        .build();
  }

  @Override
  public AiTextResponse generateJobRequirements(String title, String category) throws Exception {
    String prompt =
        """
                Generate professional job requirements and responsibilities for this role.

                Job Title: %s
                Category: %s

                Format in markdown:
                ## Responsibilities
                - [5 specific bullet points]

                ## Requirements
                - [5 specific bullet points]

                Keep it concise and ATS-friendly.
                """
            .formatted(title, category);
    return AiTextResponse.builder()
        .content(geminiClient.generateText(SYSTEM_PROMPT, prompt))
        .build();
  }

  @Override
  public SalaryRangeResponse generateSalaryRange(SalaryRangeRequest req) throws Exception {
    String skills = req.getSkills() != null ? String.join(", ", req.getSkills()) : NOT_SPECIFIED;

    String promt =
        """
                Provide a realistic and competitive salary range for this role.

                - Job Title: %s
                - Required Skills: %s
                - Experience Level: %s
                - Job Type: %s
                - Location: %s

                {
                    "minSalary": 2000,
                    "maxSalary": 4000,
                    "currency": "INR",
                    "period": "monthly",
                    "marketInsight": "Brief 1-2 sentence insight about this role's compensation trend in the current Indian market"
                }

                minSalary and maxSalary must be numbers (not strings). Use realistic current Indian market rates.
                """
            .formatted(
                req.getTitle(),
                skills,
                Objects.toString(req.getExperienceLevel(), "MID"),
                Objects.toString(req.getJobType(), "FULL_TIME"),
                Objects.toString(req.getLocation(), "India"));

    return geminiClient.generateJson(SYSTEM_PROMPT, promt, SalaryRangeResponse.class);
  }

  @Override
  public AiTextResponse generateJobResponsibilities(String title, String category)
      throws Exception {
    String promt =
        """
                Generate 6 specific, action-oriented job responsibilities for this role.

                - Job Title: %s
                - Category: %s

                Returon ONLY a plain bullet list (not headings, no markdowns headers):

                - [responsibility 1]
                - [responsibility 2]
                ...

                Keep each bullet concise (under 15 words), start with a strong action verb.
                """
            .formatted(title, category);

    return AiTextResponse.builder()
        .content(geminiClient.generateText(SYSTEM_PROMPT, promt))
        .build();
  }

  @Override
  public AiTextResponse generateJobBenefits(String title, String category, String jobType)
      throws Exception {
    String promt =
        """
                Generate 6 competitive, attractive job benefits for this role..

                - Job Title: %s
                - Category: %s
                - JobType: %s

                Returon ONLY a plain bullet list (not headings, no markdowns headers):

                - [benefit 1]
                - [benefit 2]
                ...

                Include a mix of compensation perks, health & wellness, growth, flexibility, and culture benefits.
                Keep each bullet concise and specific.
                """
            .formatted(title, category, jobType);

    return AiTextResponse.builder()
        .content(geminiClient.generateText(SYSTEM_PROMPT, promt))
        .build();
  }

  @Override
  public AiTextResponse recommendedSkillsForJob(String jobTitle, String description)
      throws Exception {
    String promt =
        """
                Recommend the most relevant skills for this job posting.

                - Job Title: %s
                - Description: %s

                List 8-20 specific, relevant skills that candidates should have.
                Return a comma-separated list of skill names only.
                Example: Java, Spring Boot, PostgreSQL, Docker, REST APIs, Microservices
                """
            .formatted(jobTitle, description);

    return AiTextResponse.builder()
        .content(geminiClient.generateText(SYSTEM_PROMPT, promt))
        .build();
  }

  @Override
  public AiTextResponse recommendTagForJob(String jobTitle, String description) throws Exception {
    String promt =
        """
                Recommend 8-16 relevant tags/keywords for this job posting tahat improve discoverability.

                - Job Title: %s
                - Description: %s

                Return ONLY a comma-separated list of short tag names (1-3 words each).
                Example: React, Frontend, JavaScript, Remote, Startup, Full Stack, Web Development
                """
            .formatted(jobTitle, description);

    return AiTextResponse.builder()
        .content(geminiClient.generateText(SYSTEM_PROMPT, promt))
        .build();
  }
}
