package com.zosh.job.mapper;

import com.zosh.job.dto.PersonalInfoResponse;
import com.zosh.job.dto.ResumeResponse;
import com.zosh.job.model.PersonalInfo;
import com.zosh.job.model.Resume;

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
}
