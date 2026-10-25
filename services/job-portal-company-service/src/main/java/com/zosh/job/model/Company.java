package com.zosh.job.model;

import com.zosh.job.db.BaseTable;
import com.zosh.job.domain.CompanySize;
import com.zosh.job.domain.CompanyStatus;
import com.zosh.job.domain.CompanyType;
import com.zosh.job.domain.IndustryType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "companies")
public class Company extends BaseTable {

  @Column(unique = true, nullable = true)
  private String name;

  private String slug;

  private String tagline;
  private String description;
  private String logoUrl;
  private String coverImageUrl;
  private String website;
  private String email;
  private String phone;
  private Integer foundedYear;

  @Enumerated(EnumType.STRING)
  private CompanySize companySize;

  @Enumerated(EnumType.STRING)
  private CompanyType companyType;

  @Enumerated(EnumType.STRING)
  private IndustryType industryType;

  @Enumerated(EnumType.STRING)
  private CompanyStatus status;

  private boolean isVerified = false;

  @Column(unique = true, nullable = false)
  private String registrationNumber;

  @Column(nullable = false, unique = true)
  private Long ownerId;

  @ElementCollection(fetch = FetchType.EAGER)
  @Builder.Default
  private List<SocialLink> socialLinks = new ArrayList<>();

  private Boolean active = true;
}
