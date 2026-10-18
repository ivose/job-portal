package com.zosh.job.dto;

import com.zosh.job.domain.CompanySize;
import com.zosh.job.domain.CompanyType;
import com.zosh.job.domain.IndustryType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class CompanyRequest {
  @NotBlank(message = "company name is required")
  private String name;

  private String slug;

  private String tagline;

  private String description;

  private String logoUrl;
  private String coverImageUrl;

  @Pattern(regexp = "^(https?://).*", message = "Website must be  a valid URL")
  private String website;

  @Email(message = "Company email must be valid")
  private String email;

  private String phone;

  @Min(value = 1800, message = "Founded year seems too old")
  @Max(value = 2100, message = "Founded year is invalid")
  private Integer foundedYear;

  @NotNull(message = "Company size is required")
  private CompanySize companySize;

  @NotNull(message = "Company type is required")
  private CompanyType companyType;

  @NotNull(message = "Industry type is required")
  private IndustryType industryType;

  @NotNull(message = "Registration number is required")
  private String registrationNumber;

  private List<SocialLinkResponse> socialLinks;
}
