package com.zosh.job.mapper;

import com.zosh.job.dto.CompanyResponse;
import com.zosh.job.dto.SocialLinkResponse;
import com.zosh.job.model.Company;
import com.zosh.job.model.SocialLink;
import java.util.Collections;
import java.util.List;

public class CompanyMapper {

  public static SocialLinkResponse toSocialLinkResponseList(SocialLink socialLink) {
    return SocialLinkResponse.builder()
        .platform(socialLink.getPlatform())
        .url(socialLink.getUrl())
        .build();
  }

  public static CompanyResponse toResponse(Company company) {
    if (company == null) {
      return null;
    }

    List<SocialLinkResponse> sociaLinks =
        company.getSocialLinks() == null
            ? Collections.emptyList()
            : company.getSocialLinks().stream()
                .map(CompanyMapper::toSocialLinkResponseList)
                .toList();

    return CompanyResponse.builder()
        .id(company.getId())
        .name(company.getName())
        .slug(company.getSlug())
        .tagline(company.getTagline())
        .description(company.getDescription())
        .logoUrl(company.getLogoUrl())
        .coverImageUrl(company.getCoverImageUrl())
        .website(company.getWebsite())
        .email(company.getEmail())
        .phone(company.getPhone())
        .foundedYear(company.getFoundedYear())
        .companySize(company.getCompanySize())
        .companyType(company.getCompanyType())
        .industryType(company.getIndustryType())
        .companyStatus(company.getStatus())
        .active(company.getActive())
        .ownerId(company.getOwnerId())
        .socialLinks(sociaLinks)
        //
        .createdAt(company.getCreatedAt())
        .updatedAt(company.getUpdatedAt())
        .build();
  }
}
