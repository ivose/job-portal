package com.zosh.job.mapper;

import com.zosh.job.dto.EducationResponse;
import com.zosh.job.dto.PersonalInfoResponse;
import com.zosh.job.dto.ResumeResponse;
import com.zosh.job.dto.ResumeSkillResponse;
import com.zosh.job.model.Education;
import com.zosh.job.model.PersonalInfo;
import com.zosh.job.model.Resume;
import com.zosh.job.model.ResumeSkill;

public class ResumeMapper {

  public static PersonalInfoResponse toPersonalInfoResponse(PersonalInfo personalInfo) {
    return personalInfo == null
        ? null
        : PersonalInfoResponse.builder()
            .firstName(personalInfo.getFirstName())
            .lastName(personalInfo.getLastName())
            .headline(personalInfo.getHeadline())
            .email(personalInfo.getEmail())
            .phone(personalInfo.getPhone())
            .city(personalInfo.getCity())
            .country(personalInfo.getCountry())
            .linkedinUrl(personalInfo.getLinkedinUrl())
            .githubUrl(personalInfo.getGithubUrl())
            .portfolioUrl(personalInfo.getPortfolioUrl())
            .websiteUrl(personalInfo.getWebsiteUrl())
            .build();
  }

  public static ResumeResponse toResponse(Resume resume) {

    return resume == null
        ? null
        : ResumeResponse.builder()
            .id(resume.getId())
            .candidateId(resume.getCandidateId())
            .title(resume.getTitle())
            .template(resume.getTemplate())
            .visibility(resume.getVisibility())
            .isDefault(resume.getIsDefault())
            .personalInfo(toPersonalInfoResponse(resume.getPersonalInfo()))
            .summary(resume.getSummary())
            .completionScore(resume.getCompletionScore())
            .createdAt(resume.getCreatedAt())
            .updatedAt(resume.getUpdatedAt())
            .build();
  }

  public static ResumeSkillResponse toSkillResponse(ResumeSkill skill) {
    return skill == null
        ? null
        : ResumeSkillResponse.builder()
            .id(skill.getId())
            .skillName(skill.getSkillName())
            .proficiencyLevel(skill.getProficiencyLevel())
            .yearsOfExperience(skill.getYearsOfExperience())
            .displayOrder(skill.getDisplayOrder())
            .build();
  }

  public static EducationResponse toEducationResponse(Education education) {
    return education == null
        ? null
        : EducationResponse.builder()
            .id(education.getId())
            .institutionName(education.getInstitutionName())
            .degree(education.getDegree())
            .fieldOfStudy(education.getFieldOfStudy())
            .grade(education.getGrade())
            .startDate(education.getStartDate())
            .endDate(education.getEndDate())
            .isCurrentEducation(education.getIsCurrentEducation())
            .description(education.getDescription())
            .displayOrder(education.getDisplayOrder())
            .build();
  }
}
