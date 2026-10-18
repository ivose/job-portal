package com.zosh.job.dto;

import com.zosh.job.domain.CompanySize;
import com.zosh.job.domain.CompanyStatus;
import com.zosh.job.domain.CompanyType;
import com.zosh.job.domain.IndustryType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponse {
  private Long id;
  private String name;
  private String slug;
  private String tagline;
  private String description;
  // private String imageUrl;
  private String logoUrl;
  private String coverImageUrl;
  private String website;
  private String email;
  private String phone;
  private Integer foundedYear;
  private CompanySize companySize;
  private CompanyType companyType;
  private IndustryType industryType;
  private CompanyStatus companyStatus;
  private Boolean active;

  private Long ownerId;
  private List<SocialLinkResponse> socialLinks;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime verifiedAt;
}
