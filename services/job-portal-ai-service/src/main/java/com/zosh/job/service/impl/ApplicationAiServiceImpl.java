package com.zosh.job.service.impl;

import com.zosh.job.client.GeminiClient;
import com.zosh.job.payload.AiTextResponse;
import com.zosh.job.payload.CoverLetterRequest;
import com.zosh.job.payload.ScreeningScoreRequest;
import com.zosh.job.payload.ScreeningScoreResponse;
import com.zosh.job.payload.SkillGapRequest;
import com.zosh.job.payload.SkillsGapResponse;
import com.zosh.job.service.ApplicationAiService;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationAiServiceImpl implements ApplicationAiService {

  private final GeminiClient geminiClient;

  private static final String SYSTEM_PROMPT =
      """
            You are a seniour tecnical  recruiter and career coach with 50+ years of experience in the Indian tech industry.
            You specialize in candidate evaluation, cover letter writing, skills gap analysis, and career development.
            Always provide objective, fair, and actiionable assesments based only on the information provided.
            When asked for JSON, respond ONLY with valid JSON - no explanation, no markdown fences.
            """;
  private static final String NOT_PROVIDED = "Not provided";

  @Override
  public AiTextResponse generateCoverLetter(CoverLetterRequest req) throws Exception {

    String prompt =
        """
                Write a compelling, personalized cover letter.

                Position: %s
                Job Description: %s
                Target Company: %s

                Candidate Profile:
                - Name: %s
                - Professional Summary: %s
                - Key Skills: %s
                - Relevant Esperience: %s

                Write a 3-paragraph cover letter:
                Paragraph 1 (Opening): Express specific enthusiasm for this exact role and company. Mention 1 specific thing about the role that excites you.
                Paragraph 2 (Body): Connect 2-3 of the candidates's strongest experiences/skills directly to the job requirements. Be specific with examples.
                Paragraph 3 (Closing): Confident call to action. Express eagerness to discuss further.

                Rules:
                - Write as the candidate (first person)
                - Be specific - avoid generic statements
                - Maximum 300 words
                - Professional but warm tone
                - Do NOT use placeholders like [Company Name] or [Job Title] - use the actual company name or say "your team"
                - Do NOT include subject line or date
                """
            .formatted(
                Objects.toString(req.getJobTitle(), NOT_PROVIDED),
                Objects.toString(req.getJobDescription(), NOT_PROVIDED),
                Objects.toString(req.getTargetCompanyName(), "your organization"),
                req.getCandidateName(),
                Objects.toString(req.getCandidateSummary(), "Esxperienced Professional"),
                getResult(req.getCandidateSkills(), NOT_PROVIDED),
                getResult(req.getCandidateExperiences(), NOT_PROVIDED));
    return AiTextResponse.builder()
        .content(geminiClient.generateText(SYSTEM_PROMPT, prompt))
        .build();
  }

  @Override
  public ScreeningScoreResponse scoreCandidate(ScreeningScoreRequest req) throws Exception {
    String prompt =
        """
                Score this job application based on how well the candidate matches the requirements.

                Job Requiements:
                - Title: %s
                - Experience Level Required: %s
                - Required Skills: %s
                - Key Responsibilities: %s

                Candidate Profile:
                - Professional Summary %s
                - Skills: %s
                - Experience History: %s

                {
                  "score": 85,
                  "skillMatchScore": 90,
                  "experienceMatchScore": 75,
                  "educationMatchScore": 70,
                  "matchedSkills": ["skill1", "skill2"],
                  "missingSkills": ["skill3"],
                  "strengths": ["strength1", "strength2"],
                  "concerns": ["concern1"],
                  "summary": "2-3 sentence honest assessment of this candidate's fit"
                }

                Score scale: 0-100 where 100 is a perfect match.
                skillsMatchScore: how well candidate skills match required skills (0-100).
                experienceMatchScore: how well candidate experience matches required level (0-100).
                educationMatchScore: how well candidate education/background fits the role (0-100),
                score: overall weighed match considering all factors. Be objective and fair.
                """
            .formatted(
                Objects.toString(req.getJobTitle(), NOT_PROVIDED),
                Objects.toString(req.getExperienceLevel(), NOT_PROVIDED),
                getResult(req.getRequiredSkills(), NOT_PROVIDED),
                Objects.toString(req.getResponsibilities(), NOT_PROVIDED),
                Objects.toString(req.getCandidateSummary(), NOT_PROVIDED),
                getResult(req.getCandidateSkills(), NOT_PROVIDED),
                getResult(req.getCandidateExperience(), NOT_PROVIDED));
    return geminiClient.generateJson(SYSTEM_PROMPT, prompt, ScreeningScoreResponse.class);
  }

  @Override
  public SkillsGapResponse anallyseSkillGap(SkillGapRequest req) throws Exception {
    String prompts =
        """
                Analyse the skills gap between a candidate and a job requiredment.

                Job Title: %s
                Candidate's Current Skills: %s
                Skills required: %s

                {
                  "matchedSkills": ["skills candidate has that are required"],
                  "missingSkills": ["required skills candidate completely lacks"],
                  "partialMatch": ["skills candidate has partially or related versions of"],
                  "prioritySkillsToLearn": ["top 3 skills to learn first, in order of importance"],
                  "learningRecommendations": [
                    {
                      "skill": "skill name",
                      "why": "why this skill is important for the role",
                      "howToLearn": "specific learning resources or approach"
                    }
                  ],
                  "overallReadiness": "Ready or Partially Ready or Needs Development",
                  "summary": "2-sentence honest assessment"
                }
                """
            .formatted(
                req.getJobTitle(),
                getResult(req.getCandidateSkills(), NOT_PROVIDED),
                getResult(req.getRequiredSkills(), NOT_PROVIDED));
    return geminiClient.generateJson(SYSTEM_PROMPT, prompts, SkillsGapResponse.class);
  }

  private String getResult(List<String> input, String def) {
    return input != null && !input.isEmpty() ? String.join(", ", input) : def;
  }
}
