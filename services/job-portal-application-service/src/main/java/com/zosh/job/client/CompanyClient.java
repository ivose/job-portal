package com.zosh.job.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CompanyClient {

  private final RestClient restClient;

  public CompanyClient(
      @Value("${company.service.url:http://localhost:5002}") String companyServiceUrl) {
    this.restClient = RestClient.builder().baseUrl(companyServiceUrl).build();
  }

  public CompanyInfo getCompanyById(Long companyId) {
    return restClient.get().uri("/api/company/{id}", companyId).retrieve().body(CompanyInfo.class);
  }

  public CompanyInfo getMyCompany(Long employerId) {
    return restClient
        .get()
        .uri("/api/company/my")
        .header("X-User-Id", employerId.toString())
        .retrieve()
        .body(CompanyInfo.class);
  }

  public record CompanyInfo(Long id, String name, Long ownerId) {}
}
