package com.zosh.job.service.impl;

import com.zosh.job.client.GeminiClient;
import com.zosh.job.payload.AiTextResponse;
import com.zosh.job.payload.CareerFeedbackRequest;
import com.zosh.job.payload.CareerFeedbackResponse;
import com.zosh.job.payload.ResumeImprovementRequest;
import com.zosh.job.payload.ResumeImprovementResponse;
import com.zosh.job.payload.ResumeSummaryRequest;
import com.zosh.job.payload.WorkExperienceBulletRequest;
import com.zosh.job.payload.WorkExperienceBulletsResponse;
import com.zosh.job.service.ResumeAiService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// @Transactional
public class ResumeAiServiceImpl implements ResumeAiService {

  private final GeminiClient geminiClient;
  String SYSTEM_PROMPT =
      """
            You are a senior resume writer and career coach with 15+ years of experience.
            You specialize in ATS-optimized resumes, career coaching, and professional branding.
            Always be specific and results-oriented.
            Never invent qualifications, achievements, or metrics.
            When asked for JSON, respond ONLY with valid JSON,
            without explanations or markdown fences.
            """;

  @Override
  public AiTextResponse generateProfessionalSummary(ResumeSummaryRequest req) throws Exception {
    String experiences =
        req.getWorkExperiences() != null
            ? req.getWorkExperiences().stream()
                .map(
                    e ->
                        e.getJobTitle()
                            + " at "
                            + e.getCompany()
                            + (e.getDescription() != null && !e.getDescription().isBlank()
                                ? ": " + e.getDescription()
                                : ""))
                .collect(Collectors.joining("; "))
            : "Note Provided";
    String skills =
        req.getSkills() != null
            ? req.getSkills().stream().collect(Collectors.joining(", "))
            : "Note Provided";
    String educations =
        req.getEducations() != null
            ? req.getEducations().stream()
                .map(
                    e ->
                        e.getDegree()
                            + (e.getFieldOfStudy() != null ? " in " + e.getFieldOfStudy() : ""))
                .collect(Collectors.joining("; "))
            : "Note Provided";
    String prompt =
        """
                Write a compelling professional summary for a resume.

                Candidate Profile:
                - Target Job Title: %s
                - Years of Experience: %d
                - Work Experience: %s
                - Key Skills: %s
                - Education: %s

                Write a 3-4 sentence professional summary that:
                1. Opens with seniority level and area of expertise
                2. Highlights 2-3 key achievements or strengths with impact
                3. Mentions specific technical skills relevant to the target role
                4. Ends with a value proposition or career goal

                Rules:
                - Write in first person (no "I" at the start)
                - Be specific and results-oriented
                - Keep it under 80 words
                - Make it ATS-friendly
                """
            .formatted(
                req.getTargetJobTitle() != null ? req.getTargetJobTitle() : "Software Developer",
                req.getYearOfExperience() != null ? req.getYearOfExperience() : 0,
                experiences,
                skills,
                educations);

    return AiTextResponse.builder()
        .content(geminiClient.generateText(SYSTEM_PROMPT, prompt))
        .build();
  }

  @Override
  public WorkExperienceBulletsResponse generateWorkExperienceBullets(
      WorkExperienceBulletRequest req) throws Exception {
    String prompt =
        """
                Transform this work experience into powerful, ATS-friendly resume bullet points.

                Role: %s
                Company: %s
                Raw Description: %s
                Achievements/Hints: %s

                Generate exactly 4-5 bullet points that:
                1. Start with strong action verbs (Developed, Led, Implemented, Architected, Optimized, Reduced, Increased, Delivered, Built)
                2. Include quantifiable metrics where possible (percentages, numbers, time saved).
                3. Highlight business impact and technical achievements
                4. Are concise (under 20 words each)
                5. Are ATS-friendly with relevant keywords

                Return JSON in this format:
                {
                "bullets": ["bullet point 1", "bullet point 2", "bullet point 3", "bullet point 4", "bullet point 5"]
                }
                """
            .formatted(
                req.getJobTitle(),
                req.getCompany() != null ? req.getCompany() : "the company",
                req.getRawDescription(),
                req.getAchievementsHint() != null ? req.getAchievementsHint() : "None");
    return geminiClient.generateJson(SYSTEM_PROMPT, prompt, WorkExperienceBulletsResponse.class);
  }

  @Override
  public CareerFeedbackResponse generateCareerFeedback(CareerFeedbackRequest req) throws Exception {
    String prompt =
        """
                Analyze this resume and deliver an honest, actionable career feedback report.

                Target Job Title (if provided): is %s
                Resume Content: %s

                {
                "profileStrength": 65,
                "shortlistingIssues" ["Reason 1 why recruiters are skipping this profile", "Reason 2", "Reason 3"],
                  "improvements": [
                    {"area": "skills | Summary | Experience | Education | Projects | General", "issue": "What specifically is weak or missing", "action": "Concrete step the candidate should take to fix it" "priority": "HIGH | MEDIUM | LOW"}
                  ],
                  "targetJobs": [
                    { "jobTitle": "recommended Job Title", "reason": "Why this role suits the current profile", "skillMatch": "HIGH | MEDIUM | LOW" }
                  ],
                  "overallSummary": "2-3 sentences of honest, encouraging career advice"
                }

                Rules:
                - profileStrength: integer 0-100 reflecting overall job market readiness
                - shortlistingIssues: 3-5 candid reasons a recruiter would skip this resume
                - improvements: 4-6 items ordered by priority descending
                - targetJobs: 3-5 realistic job titles matching current skills and esperience level
                - Be specific - mention actual skills, tools, or sections by name
                """
            .formatted(
                req.getTargetJobTitle() != null ? req.getTargetJobTitle() : "Not specified",
                req.getResumeContent());
    return geminiClient.generateJson(SYSTEM_PROMPT, prompt, CareerFeedbackResponse.class);
  }

  @Override
  public ResumeImprovementResponse getResumeImprovementTips(ResumeImprovementRequest req)
      throws Exception {
    String prompts =
        """
                Analyse this resume and provide specific, actionable improvement suggestions.

                Target Job Title: %s

                Resume Content:
                %s

                {
                 "overallScore": 72,
                 "improvements": [
                   { "section": "Summary or Experience or Skill or Education or General", "issue": "What is wrong or missing", "suggestion": "Specific action to fix it", "priority": "HIGH or MEDIUM or LOW" },
                 ],
                 "strengths": ["what is already good about this resume"],
                 "summary": "2-sentence overall assessment"
                }

                Provide 4-6 specific improvements. Score should be 0-100
                """
            .formatted(
                req.getTargetJobTitle() != null ? req.getTargetJobTitle() : "Not specified",
                req.getResumeContent());
    return geminiClient.generateJson(SYSTEM_PROMPT, prompts, ResumeImprovementResponse.class);
  }
}
