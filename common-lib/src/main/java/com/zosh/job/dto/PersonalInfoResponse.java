package com.zosh.job.dto;

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
public class PersonalInfoResponse {

  private String firstName;
  private String lastName;

  private String headline;

  private String email;
  private String phone;
  private String city;
  private String country;

  private String linkedinUrl;
  private String githubUrl;
  private String portfolioUrl;
  private String websiteUrl;
}
