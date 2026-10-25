package com.zosh.job.client;

import com.zosh.job.dto.CompanyResponse;
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

  public Long getCompanyIdByEmployerId(Long employerId) {

    CompanyInfo company =
        restClient
            .get()
            .uri("/api/company/my")
            .header("X-User-Id", employerId.toString())
            .retrieve()
            .body(CompanyInfo.class);

    if (company == null || company.id() == null) {
      throw new IllegalStateException("No company found for employer " + employerId);
    }

    return company.id();
  }

  public CompanyResponse getCompanyById(Long companyId) {
    return restClient
        .get()
        .uri("/api/company/{id}", companyId)
        .retrieve()
        .body(CompanyResponse.class);
  }

  private record CompanyInfo(Long id, String name) {}
}
